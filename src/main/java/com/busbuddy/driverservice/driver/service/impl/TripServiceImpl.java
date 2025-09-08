package com.busbuddy.driverservice.driver.service.impl;

import com.busbuddy.driverservice.driver.model.Driver;
import com.busbuddy.driverservice.driver.model.RouteAssignment;
import com.busbuddy.driverservice.driver.model.Trip;
import com.busbuddy.driverservice.driver.repository.DriverRepository;
import com.busbuddy.driverservice.driver.repository.RouteAssignmentRepository;
import com.busbuddy.driverservice.driver.repository.TripRepository;
import com.busbuddy.driverservice.common.constants.ErrorCodes;
import com.busbuddy.driverservice.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements com.busbuddy.driverservice.driver.service.TripService {

    private final TripRepository tripRepository;
    private final RouteAssignmentRepository routeAssignmentRepository;
    private final DriverRepository driverRepository;

    @Override
    public String startTrip(String licenseNumber) {
        Driver driver = driverRepository.findByLicenseNumber(licenseNumber)
                .orElseThrow(() -> new BusinessException(ErrorCodes.DRIVER_NOT_FOUND,
                        "Driver not found with license: " + licenseNumber));

        RouteAssignment assignment = routeAssignmentRepository.findByDriver(driver)
                .orElseThrow(() -> new BusinessException(ErrorCodes.ROUTE_ASSIGNMENT_NOT_FOUND,
                        "No assignment found for driver: " + driver.getDriverName()));

        LocalDate today = LocalDate.now();

        // Check if there's an ongoing trip today
        List<Trip> todaysTrips = tripRepository.findByAssignmentAndTripDate(assignment, today);
        boolean inProgress = todaysTrips.stream().anyMatch(t -> "IN_PROGRESS".equals(t.getStatus()));
        if (inProgress) {
            throw new BusinessException("TRIP_ALREADY_STARTED", "Trip already in progress today");
        }

        // Find the last trip sequence for today
        int nextSequence = tripRepository.findTopByAssignmentAndTripDateOrderByTripSequenceDesc(assignment, today)
                .map(t -> t.getTripSequence() + 1)
                .orElse(1);

        Trip trip = Trip.builder()
                .assignment(assignment)
                .tripDate(today)
                .tripSequence(nextSequence)
                .status("IN_PROGRESS")
                .startTime(LocalDateTime.now())
                .build();

        tripRepository.save(trip);
        return "Trip #" + nextSequence + " started successfully";
    }

    @Override
    public String completeTrip(String licenseNumber) {
        Driver driver = driverRepository.findByLicenseNumber(licenseNumber)
                .orElseThrow(() -> new BusinessException(ErrorCodes.DRIVER_NOT_FOUND,
                        "Driver not found with license: " + licenseNumber));

        RouteAssignment assignment = routeAssignmentRepository.findByDriver(driver)
                .orElseThrow(() -> new BusinessException(ErrorCodes.ROUTE_ASSIGNMENT_NOT_FOUND,
                        "No assignment found for driver: " + driver.getDriverName()));

        LocalDate today = LocalDate.now();

        // Get the latest IN_PROGRESS trip
        Trip trip = tripRepository.findByAssignmentAndTripDate(assignment, today).stream()
                .filter(t -> "IN_PROGRESS".equals(t.getStatus()))
                .findFirst()
                .orElseThrow(() -> new BusinessException("TRIP_NOT_FOUND", "No active trip to complete"));

        trip.setStatus("COMPLETED");
        trip.setEndTime(LocalDateTime.now());

        tripRepository.save(trip);
        return "Trip #" + trip.getTripSequence() + " completed successfully";
    }
}

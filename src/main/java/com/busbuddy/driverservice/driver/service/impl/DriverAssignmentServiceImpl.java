package com.busbuddy.driverservice.driver.service.impl;

import com.busbuddy.driverservice.driver.dto.DriverAssignmentResponse;
import com.busbuddy.driverservice.driver.model.Driver;
import com.busbuddy.driverservice.driver.model.Route;
import com.busbuddy.driverservice.driver.model.RouteAssignment;
import com.busbuddy.driverservice.driver.model.RouteStop;
import com.busbuddy.driverservice.driver.repository.DriverRepository;
import com.busbuddy.driverservice.driver.repository.RouteAssignmentRepository;
import com.busbuddy.driverservice.driver.repository.RouteStopRepository;
import com.busbuddy.driverservice.driver.service.DriverAssignmentService;
import com.busbuddy.driverservice.common.constants.ErrorCodes;
import com.busbuddy.driverservice.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverAssignmentServiceImpl implements DriverAssignmentService {

    private final DriverRepository driverRepository;
    private final RouteAssignmentRepository routeAssignmentRepository;
    private final RouteStopRepository routeStopRepository;

    @Override
    public DriverAssignmentResponse getDriverAssignmentByLicense(String licenseNumber) {
        // 1. Find driver
        Driver driver = driverRepository.findByLicenseNumber(licenseNumber)
                .orElseThrow(() -> new BusinessException(
                        ErrorCodes.DRIVER_NOT_FOUND, "Driver not found with license: " + licenseNumber));

        // 2. Find route assignment
        RouteAssignment assignment = routeAssignmentRepository.findByDriver(driver)
                .orElseThrow(() -> new BusinessException(
                        ErrorCodes.ROUTE_ASSIGNMENT_NOT_FOUND, "No assignment found for driver: " + driver.getDriverName()));

        Route route = assignment.getRoute();

        // 3. Get ordered stops for route
        List<RouteStop> routeStops = routeStopRepository.findByRouteOrderBySequenceNumberAsc(route);

        List<String> stopNames = routeStops.stream()
                .map(rs -> rs.getStop().getStopName())
                .collect(Collectors.toList());

        // 4. Build response
        return DriverAssignmentResponse.builder()
                .driverName(driver.getDriverName())
                .busRegNumber(assignment.getBus().getRegNumber())
                .routeName(route.getRouteName())
                .origin(route.getOrigin())
                .destination(route.getDestination())
                .stops(stopNames)
                .build();
    }
}

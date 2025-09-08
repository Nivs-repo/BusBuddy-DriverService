package com.busbuddy.driverservice.driver.repository;

import com.busbuddy.driverservice.driver.model.Trip;
import com.busbuddy.driverservice.driver.model.RouteAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByAssignmentAndTripDate(RouteAssignment assignment, LocalDate tripDate);
    Optional<Trip> findTopByAssignmentAndTripDateOrderByTripSequenceDesc(RouteAssignment assignment, LocalDate tripDate);
}

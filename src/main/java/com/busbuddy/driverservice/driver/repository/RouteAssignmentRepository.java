package com.busbuddy.driverservice.driver.repository;

import com.busbuddy.driverservice.driver.model.RouteAssignment;
import com.busbuddy.driverservice.driver.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RouteAssignmentRepository extends JpaRepository<RouteAssignment, Long> {
    Optional<RouteAssignment> findByDriver(Driver driver);
    List<RouteAssignment> findAllByDriver(Driver driver);
}
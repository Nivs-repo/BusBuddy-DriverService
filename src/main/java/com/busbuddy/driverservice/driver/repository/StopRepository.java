package com.busbuddy.driverservice.driver.repository;

import com.busbuddy.driverservice.driver.model.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StopRepository extends JpaRepository<Stop, Long> {
    Optional<Stop> findByStopNameIgnoreCase(String stopName);
}
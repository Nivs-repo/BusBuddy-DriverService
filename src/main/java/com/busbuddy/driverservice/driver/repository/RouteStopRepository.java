package com.busbuddy.driverservice.driver.repository;

import com.busbuddy.driverservice.driver.model.RouteStop;
import com.busbuddy.driverservice.driver.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteStopRepository extends JpaRepository<RouteStop, Long> {
    List<RouteStop> findByRouteOrderBySequenceNumberAsc(Route route);
}
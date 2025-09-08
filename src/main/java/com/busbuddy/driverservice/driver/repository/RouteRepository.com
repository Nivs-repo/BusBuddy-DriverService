package com.tcs.busbuddy.driver.repository;

import com.tcs.busbuddy.driver.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route, Long> {
    Optional<Route> findByRouteNameIgnoreCase(String routeName);
}
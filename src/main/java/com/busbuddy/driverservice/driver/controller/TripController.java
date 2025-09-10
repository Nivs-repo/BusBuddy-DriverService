package com.busbuddy.driverservice.driver.controller;

import com.busbuddy.driverservice.driver.service.TripService;
import com.busbuddy.driverservice.common.util.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @PostMapping("/{licenseNo}/start")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<?> startTrip(@PathVariable String licenseNo) {
        return ResponseBuilder.buildSuccessResponse(tripService.startTrip(licenseNo));
    }

    @PostMapping("/{licenseNo}/complete")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<?> completeTrip(@PathVariable String licenseNo) {
        return ResponseBuilder.buildSuccessResponse(tripService.completeTrip(licenseNo));
    }
}

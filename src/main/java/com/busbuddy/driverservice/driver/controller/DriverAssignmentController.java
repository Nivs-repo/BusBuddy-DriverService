package com.busbuddy.driverservice.driver.controller;

import com.busbuddy.driverservice.common.util.ResponseBuilder;
import com.busbuddy.driverservice.driver.dto.DriverAssignmentResponse;
import com.busbuddy.driverservice.driver.service.DriverAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/driver")
@RequiredArgsConstructor
public class DriverAssignmentController {

    private final DriverAssignmentService driverAssignmentService;

    /**
     * Get current assignment (bus, route, stops) for a driver by license number
     */
    @GetMapping("/{licenseNo}/assignments")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<?> getDriverAssignment(@PathVariable String licenseNo) {
        DriverAssignmentResponse response = driverAssignmentService.getDriverAssignmentByLicense(licenseNo);
        return ResponseBuilder.buildSuccessResponse(response);
    }
}

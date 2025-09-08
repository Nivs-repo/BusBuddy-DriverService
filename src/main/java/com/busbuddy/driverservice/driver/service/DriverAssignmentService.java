package com.busbuddy.driverservice.driver.service;

import com.busbuddy.driverservice.driver.dto.DriverAssignmentResponse;

public interface DriverAssignmentService {
    DriverAssignmentResponse getDriverAssignmentByLicense(String licenseNumber);
}

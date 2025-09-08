package com.busbuddy.driverservice.driver.service;

public interface TripService {
    String startTrip(String licenseNumber);
    String completeTrip(String licenseNumber);
}

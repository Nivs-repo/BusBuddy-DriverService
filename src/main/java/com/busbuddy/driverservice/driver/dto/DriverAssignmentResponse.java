package com.busbuddy.driverservice.driver.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverAssignmentResponse {
    private String driverName;
    private String busRegNumber;
    private String routeName;
    private String origin;
    private String destination;
    private List<String> stops; // only stop names in sequence
}

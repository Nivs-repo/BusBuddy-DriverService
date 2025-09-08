package com.busbuddy.driverservice.driver.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "route_stop",
       uniqueConstraints = @UniqueConstraint(columnNames = {"route_id", "stop_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteStop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stop_id", nullable = false)
    private Stop stop;

    @Column(name = "sequence_number")
    private Integer sequenceNumber;
}

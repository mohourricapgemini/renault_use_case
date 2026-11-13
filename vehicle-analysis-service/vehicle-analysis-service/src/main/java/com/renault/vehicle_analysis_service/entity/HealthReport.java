package com.renault.vehicle_analysis_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HealthReport {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String vin;

    private Integer healthScore;

    private String status;

    private Instant analysisDate;
}

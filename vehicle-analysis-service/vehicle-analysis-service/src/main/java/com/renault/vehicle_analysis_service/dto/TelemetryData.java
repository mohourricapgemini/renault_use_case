package com.renault.vehicle_analysis_service.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TelemetryData {

    private String vin;

    private Integer mileage;

    private Double tirePressure;

    private Integer batteryLevel;

    private Integer engineTemperature;

    private Integer fuelLevel;

    private String errorCodes;

    private Instant timestamp;
}

package com.renault.telemetry_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class TelemetryData {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "VIN is required")
    private String vin;

    @NotNull
    @Min(0)
    private Integer mileage;

    @NotNull
    @DecimalMin("0.0")
    private Double tirePressure;

    @NotNull
    @Min(value = 0, message = "Battery level cannot be negative")
    @Max(value = 100, message = "Battery level cannot exceed 100")
    private Integer batteryLevel;

    @NotNull
    private Integer engineTemperature;

    @NotNull
    @Min(value = 0, message = "Fuel level cannot be negative")
    @Max(value = 100, message = "Fuel level cannot exceed 100")
    private Integer fuelLevel;

    private String errorCodes;

    private Instant timestamp;
}

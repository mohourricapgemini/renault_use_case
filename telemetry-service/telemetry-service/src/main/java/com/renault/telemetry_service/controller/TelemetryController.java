package com.renault.telemetry_service.controller;

import com.renault.telemetry_service.entity.TelemetryData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.renault.telemetry_service.service.TelemetryService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TelemetryController {

    private final TelemetryService telemetryService;

    @PostMapping("/telemetry")
    public ResponseEntity<?> receiveTelemetry(@Valid @RequestBody TelemetryData data) {
        System.out.println("Received telemetry data: " + data);
        telemetryService.save(data);
        return ResponseEntity.accepted().build();
    }


}

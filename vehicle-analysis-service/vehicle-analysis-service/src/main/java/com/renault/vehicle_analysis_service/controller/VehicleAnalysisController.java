package com.renault.vehicle_analysis_service.controller;

import com.renault.vehicle_analysis_service.dto.TelemetryData;
import com.renault.vehicle_analysis_service.entity.Alert;
import com.renault.vehicle_analysis_service.entity.HealthReport;
import com.renault.vehicle_analysis_service.service.VehicleAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class VehicleAnalysisController {
    private final VehicleAnalysisService vehicleAnalysisService;

    @PostMapping("/analysis")
    public ResponseEntity<?> analyze(@RequestBody TelemetryData data) {
        // Call the service to perform vehicle analysis
        vehicleAnalysisService.analyzeVehicle(data);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/vehicles/{vin}/health-report")
    public ResponseEntity<HealthReport> getHealthReport(
            @PathVariable String vin) {

        return ResponseEntity.ok(
                vehicleAnalysisService.getHealthReport(vin));
    }

    @GetMapping("/vehicles/{vin}/alerts")
    public ResponseEntity<List<Alert>> getAlerts(
            @PathVariable String vin) {

        return ResponseEntity.ok(
                vehicleAnalysisService.getAlerts(vin));
    }


}

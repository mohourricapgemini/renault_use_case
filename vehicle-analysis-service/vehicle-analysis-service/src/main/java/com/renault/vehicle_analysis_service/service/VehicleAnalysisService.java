package com.renault.vehicle_analysis_service.service;

//import com.renault.vehicle_analysis_service.client.RecallClient;
//import com.renault.vehicle_analysis_service.dto.RecallResponse;
import com.renault.vehicle_analysis_service.dto.TelemetryData;
import com.renault.vehicle_analysis_service.entity.Alert;
import com.renault.vehicle_analysis_service.entity.HealthReport;
import com.renault.vehicle_analysis_service.exception.ResourceNotFoundException;
import com.renault.vehicle_analysis_service.repository.AlertRepository;
import com.renault.vehicle_analysis_service.repository.HealthReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleAnalysisService {

    private final HealthReportRepository healthReportRepository;
    private final AlertRepository alertRepository;
    //private final RecallClient recallClient;


    public HealthReport getHealthReport(String vin) {

        return healthReportRepository
                .findTopByVinOrderByAnalysisDateDesc(vin)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No health report found for VIN: " + vin));
    }

    public List<Alert> getAlerts(String vin) {

        List<Alert> alerts = alertRepository.findByVin(vin);

        if (alerts.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No alerts found for VIN: " + vin);
        }

        return alerts;
    }

    public void analyzeVehicle(TelemetryData data) {
        List<Alert> alerts = new ArrayList<>();

        int score = calculateHealthScore(data, alerts);

        if (!alerts.isEmpty()) {
            alertRepository.saveAll(alerts);
        }


        HealthReport report = new HealthReport();

        report.setVin(data.getVin());
        report.setHealthScore(score);
        report.setAnalysisDate(Instant.now());
        report.setStatus(determineStatus(score));

        healthReportRepository.save(report);
    }

    public int calculateHealthScore(TelemetryData data, List<Alert> alerts) {
        int score = 100;

        if (data.getMileage() > 100000) {
            alerts.add(createAlert(
                    data.getVin(),
                    "HIGH_MILEAGE",
                    "MEDIUM",
                    "Mileage exceeds threshold")
            );
            score -= 10;
        }

        if (data.getTirePressure() < 2.0) {
            alerts.add(createAlert(
                    data.getVin(),
                    "LOW_TIRE_PRESSURE",
                    "HIGH",
                    "Tire pressure below threshold")
            );
            score -= 15;
        }

        if (data.getBatteryLevel() < 30) {
            alerts.add(createAlert(
                    data.getVin(),
                    "LOW_BATTERY",
                    "HIGH",
                    "Battery level below threshold")
            );
            score -= 15;
        }

        if (data.getEngineTemperature() > 110) {
            alerts.add(createAlert(
                    data.getVin(),
                    "ENGINE_OVERHEAT",
                    "CRITICAL",
                    "Engine temperature exceeds threshold")
            );
            score -= 20;
        }

        if (data.getFuelLevel() < 10) {
            alerts.add(createAlert(
                    data.getVin(),
                    "LOW_FUEL",
                    "MEDIUM",
                    "Fuel level below threshold")
            );
            score -= 5;
        }

        if (data.getErrorCodes() != null
                && !data.getErrorCodes().isBlank()) {
            alerts.add(createAlert(
                    data.getVin(),
                    "ERROR_CODE_PRESENT",
                    "HIGH",
                    "Vehicle contains error codes")
            );
            score -= 15;
        }
    /*
        RecallResponse recall =
                recallClient.getRecallStatus(data.getVin());

        if (recall.activeRecall()) {

            alerts.add(createAlert(
                    data.getVin(),
                    "ACTIVE_RECALL",
                    "CRITICAL",
                    "Vehicle has an active recall campaign"));

            score -= 20;
        }
    */
        return Math.max(score, 0);
    }

    private Alert createAlert(
            String vin,
            String type,
            String severity,
            String message) {

        return new Alert(
                null,
                vin,
                type,
                severity,
                message,
                Instant.now()
        );
    }

    private String determineStatus(int score) {

        if (score >= 80) {
            return "HEALTHY";
        }

        if (score >= 50) {
            return "WARNING";
        }

        return "CRITICAL";
    }
}

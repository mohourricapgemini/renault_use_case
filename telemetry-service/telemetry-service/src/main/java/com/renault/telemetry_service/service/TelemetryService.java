package com.renault.telemetry_service.service;

import com.renault.telemetry_service.client.AnalysisClient;
import com.renault.telemetry_service.entity.TelemetryData;
import com.renault.telemetry_service.exception.AnalysisServiceUnavailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.renault.telemetry_service.repository.TelemetryRepository;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TelemetryService {

    private final TelemetryRepository telemetryRepository;
    private final AnalysisClient analysisClient;

    public void save(TelemetryData telemetryData) {
        telemetryData.setTimestamp(Instant.now());

        telemetryRepository.save(telemetryData);
        try {
            analysisClient.anlyzeVehicle(telemetryData);
        } catch (Exception ex) {
            throw new AnalysisServiceUnavailableException(
                    "Vehicle Analysis Service is unavailable");
        }
    }

}

package com.renault.telemetry_service.client;

import com.renault.telemetry_service.entity.TelemetryData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AnalysisClient {
    private final RestClient restClient;

    public AnalysisClient(@Value("${analysis.service.url}") String serviceUrl) {
        this.restClient = RestClient.builder().baseUrl(serviceUrl).build();
    }

    public void anlyzeVehicle(TelemetryData telemetryData) {
        restClient.post().uri("/api/v1/analysis")
                .body(telemetryData)
                .retrieve()
                .toBodilessEntity();
    }
}

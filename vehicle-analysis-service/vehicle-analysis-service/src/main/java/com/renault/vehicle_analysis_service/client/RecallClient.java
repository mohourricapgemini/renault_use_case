package com.renault.vehicle_analysis_service.client;

import com.renault.vehicle_analysis_service.dto.RecallResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RecallClient {

    private final RestClient restClient;

    public RecallClient(
            @Value("${recall.service.url}") String url) {

        this.restClient = RestClient.builder()
                .baseUrl(url)
                .build();
    }

    public RecallResponse getRecallStatus(String vin) {

        return restClient.get()
                .uri("/api/v1/recalls/{vin}", vin)
                .retrieve()
                .body(RecallResponse.class);
    }
}

package com.renault.telemetry_service.tu;

import com.renault.telemetry_service.client.AnalysisClient;
import com.renault.telemetry_service.entity.TelemetryData;
import com.renault.telemetry_service.repository.TelemetryRepository;
import com.renault.telemetry_service.service.TelemetryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TelemetryServiceTest {

    @Mock
    private TelemetryRepository telemetryRepository;

    @Mock
    private AnalysisClient analysisClient;

    @InjectMocks
    private TelemetryService telemetryService;

    @Test
    void shouldSaveTelemetryAndCallAnalysisService() {

        TelemetryData telemetry = new TelemetryData();
        telemetry.setVin("VF1R123456000");

        telemetryService.save(telemetry);

        verify(telemetryRepository).save(telemetry);
        verify(analysisClient).anlyzeVehicle(telemetry);

        assertNotNull(telemetry.getTimestamp());
    }
}

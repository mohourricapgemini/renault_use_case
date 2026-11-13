package com.renault.vehicle_analysis_service.tu;
import com.renault.vehicle_analysis_service.entity.HealthReport;
import com.renault.vehicle_analysis_service.exception.ResourceNotFoundException;
import com.renault.vehicle_analysis_service.repository.HealthReportRepository;
import com.renault.vehicle_analysis_service.service.VehicleAnalysisService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VehicleAnalysisServiceTest {

    @Mock
    private HealthReportRepository healthReportRepository;

    @InjectMocks
    private VehicleAnalysisService vehicleAnalysisService;


    // Le repository retourne un rapport.
    @Test
    void shouldReturnHealthReport() {

        HealthReport report = new HealthReport();
        report.setVin("VF1R123456000");
        report.setHealthScore(95);

        when(
                healthReportRepository
                        .findTopByVinOrderByAnalysisDateDesc(
                                "VF1R123456000"
                        )
        ).thenReturn(Optional.of(report));

        HealthReport result =
                vehicleAnalysisService.getHealthReport(
                        "VF1R123456000"
                );

        assertNotNull(result);

        assertEquals(
                "VF1R123456000",
                result.getVin()
        );

        assertEquals(
                95,
                result.getHealthScore()
        );
    }

    // Aucun rapport trouvé.
    @Test
    void shouldThrowExceptionWhenReportNotFound() {

        when(
                healthReportRepository
                        .findTopByVinOrderByAnalysisDateDesc(
                                "UNKNOWN"
                        )
        ).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,

                () -> vehicleAnalysisService
                        .getHealthReport("UNKNOWN")
        );
    }

    // Vérifier le message d'erreur
    @Test
    void shouldThrowExceptionWithCorrectMessage() {

        when(
                healthReportRepository
                        .findTopByVinOrderByAnalysisDateDesc(
                                "UNKNOWN"
                        )
        ).thenReturn(Optional.empty());

        ResourceNotFoundException ex =
                assertThrows(
                        ResourceNotFoundException.class,

                        () -> vehicleAnalysisService
                                .getHealthReport("UNKNOWN")
                );

        assertEquals(
                "No health report found for VIN: UNKNOWN",
                ex.getMessage()
        );
    }

    // Vérifier l'appel au repository
    @Test
    void shouldCallRepository() {

        HealthReport report = new HealthReport();

        when(
                healthReportRepository
                        .findTopByVinOrderByAnalysisDateDesc(
                                "VF1R123456000"
                        )
        ).thenReturn(Optional.of(report));

        vehicleAnalysisService
                .getHealthReport("VF1R123456000");

        Mockito.verify(healthReportRepository)
                .findTopByVinOrderByAnalysisDateDesc(
                        "VF1R123456000"
                );
    }

}

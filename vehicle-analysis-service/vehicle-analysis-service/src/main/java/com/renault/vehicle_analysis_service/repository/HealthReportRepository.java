package com.renault.vehicle_analysis_service.repository;

import com.renault.vehicle_analysis_service.entity.HealthReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HealthReportRepository extends JpaRepository<HealthReport, UUID> {

    Optional<HealthReport> findTopByVinOrderByAnalysisDateDesc(String vin);

}

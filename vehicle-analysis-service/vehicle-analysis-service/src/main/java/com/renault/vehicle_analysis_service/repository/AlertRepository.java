package com.renault.vehicle_analysis_service.repository;

import com.renault.vehicle_analysis_service.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AlertRepository extends JpaRepository<Alert, UUID> {
    public List<Alert> findByVin(String vin);
}

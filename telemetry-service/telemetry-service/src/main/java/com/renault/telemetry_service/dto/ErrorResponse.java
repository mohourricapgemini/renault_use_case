package com.renault.telemetry_service.dto;

import java.time.Instant;

public record ErrorResponse(
        Instant timestamp,
        int status,
        String error,
        Object message
) {
}

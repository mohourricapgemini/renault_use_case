package com.renault.telemetry_service.exception;

public class AnalysisServiceUnavailableException extends RuntimeException {
    private String message;

    public AnalysisServiceUnavailableException(String message) {
        super(message);
    }
}

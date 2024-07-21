package com.intuit.PaymentAPI.dto;

public enum PaymentStatus {
    SUCCESS("SUCCESS"),
    FAILURE("FAILURE"),
    PENDING("PENDING");

    private final String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}

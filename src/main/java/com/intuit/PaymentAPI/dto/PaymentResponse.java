package com.intuit.PaymentAPI.dto;

public class PaymentResponse {
    private String paymentId;  // Unique identifier of the payment
    private PaymentStatus status;  // Status of the payment
    private String message;  // Message providing additional information

    // Constructor
    public PaymentResponse(String paymentId, PaymentStatus status, String message) {
        this.paymentId = paymentId;
        this.status = status;
        this.message = message;
    }

    // Getters and Setters
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

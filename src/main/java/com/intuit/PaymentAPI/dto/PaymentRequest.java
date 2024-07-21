package com.intuit.PaymentAPI.dto;

import com.intuit.PaymentAPI.entity.Payment;

public class PaymentRequest {
    private double amount;  // Amount to be paid
    private String currency;  // Currency in which the payment is made
    private String payerId;  // Unique identifier of the user making the payment
    private String payeeId;  // Unique identifier of the payee
    private String paymentMethodId;  // Unique identifier of the payment method

    public PaymentRequest(double amount, String currency, String payerId, String payeeId, String paymentMethodId) {
        this.amount = amount;
        this.currency = currency;
        this.payerId = payerId;
        this.payeeId = payeeId;
        this.paymentMethodId = paymentMethodId;
    }

    // Getters and Setters
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(String payeeId) {
        this.payeeId = payeeId;
    }

    public String getPaymentMethod() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
}

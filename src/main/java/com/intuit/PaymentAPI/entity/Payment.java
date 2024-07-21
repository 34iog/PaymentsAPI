package com.intuit.PaymentAPI.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
//@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private Double amount;
    private String currency;
    private UUID payerId;
    private UUID payeeId;
    private UUID paymentMethodId;

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public UUID getpayerId() {
        return payerId;
    }

    public void setpayerId(UUID payerId) {
        this.payerId = payerId;
    }

    public UUID getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(UUID payeeId) {
        this.payeeId = payeeId;
    }

    public UUID getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(UUID paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
}
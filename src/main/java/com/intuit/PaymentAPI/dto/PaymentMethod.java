package com.intuit.PaymentAPI.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

// Base class
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreditCard.class, name = "CREDIT_CARD"),
        @JsonSubTypes.Type(value = BankAccount.class, name = "BANK")
})
public abstract class PaymentMethod {
    private String paymentMethodId;
    private String type;

    // Constructor
    public PaymentMethod(String paymentMethodId, String type) {
        this.paymentMethodId = paymentMethodId;
        this.type = type;
    }

    // Getters and setters
    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

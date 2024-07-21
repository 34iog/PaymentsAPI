package com.intuit.PaymentAPI.dto;

// Credit Card class
public class CreditCard extends PaymentMethod {
    private String name;
    private String number;
    private String cvv;
    private String expirationDate;

    // Constructor
    public CreditCard(String paymentMethodId, String name, String number, String cvv, String expirationDate) {
        super(paymentMethodId, "CREDIT_CARD");
        this.name = name;
        this.number = number;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
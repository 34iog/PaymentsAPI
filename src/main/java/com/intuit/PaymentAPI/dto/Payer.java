package com.intuit.PaymentAPI.dto;

// Assumes payers are already signed up on Intuit
// Placeholder class
public class Payer {
    private String payerId;  // Unique identifier of the payer
    private String name;  // Name of the payer
    private String email;  // Email of the payer

    // Constructor
    public Payer(String payerId, String name, String email) {
        this.payerId = payerId;
        this.name = name;
        this.email = email;
    }

    // Getters and Setters
    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package com.intuit.PaymentAPI.dto;

// Assumes payees are already signed up on Intuit
// Placeholder class
public class Payee {
    private String payeeId;  // Unique identifier of the payee
    private String name;  // Name of the payee
    private String email;  // Email of the payee

    // Constructor
    public Payee(String payeeId, String name, String email) {
        this.payeeId = payeeId;
        this.name = name;
        this.email = email;
    }

    // Getters and Setters
    public String getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(String payeeId) {
        this.payeeId = payeeId;
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

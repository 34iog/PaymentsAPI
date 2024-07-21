package com.intuit.PaymentAPI.dto;

// Bank Account class
public class BankAccount extends PaymentMethod {
    private String accountNumber;
    private String routingNumber;
    private String accountHolderName;

    // Constructor
    public BankAccount(String paymentMethodId, String accountNumber, String routingNumber, String accountHolderName) {
        super(paymentMethodId, "BANK");
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
        this.accountHolderName = accountHolderName;
    }

    // Getters and setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }
}
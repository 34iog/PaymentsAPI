package com.intuit.PaymentAPI.controller;

import com.intuit.PaymentAPI.dto.PaymentRequest;
import com.intuit.PaymentAPI.dto.PaymentResponse;
import com.intuit.PaymentAPI.dto.PaymentMethod;
import com.intuit.PaymentAPI.dto.Payee;
import com.intuit.PaymentAPI.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payments")
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest paymentRequest) {
        // Perform validation (e.g., amount should be positive)
        if (paymentRequest.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        // Create payment
        PaymentResponse response = paymentService.createPayment(paymentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/payment-methods")
    public ResponseEntity<List<PaymentMethod>> getPaymentMethods(@RequestParam String payerId) {
        List<PaymentMethod> methods = paymentService.getPaymentMethods(payerId);
        return ResponseEntity.ok(methods);
    }

    @PostMapping("/payment-methods/{payerId}")
    public ResponseEntity<PaymentMethod> createPaymentMethod(@PathVariable String payerId, @RequestBody PaymentMethod paymentMethod) {
        paymentService.createPaymentMethod(payerId, paymentMethod);
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentMethod);
    }

    @PutMapping("/payment-methods/{payerId}/{paymentMethodId}")
    public ResponseEntity<PaymentMethod> updatePaymentMethod(@PathVariable String payerId, @PathVariable String paymentMethodId, @RequestBody PaymentMethod paymentMethod) {
        paymentService.updatePaymentMethod(payerId, paymentMethodId, paymentMethod);
        return ResponseEntity.ok(paymentMethod);
    }

    @DeleteMapping("/payment-methods/{payerId}/{paymentMethodId}")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable String payerId, @PathVariable String paymentMethodId) {
        paymentService.deletePaymentMethod(payerId, paymentMethodId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/payees")
    public ResponseEntity<List<String>> getPayees(String payerId) {
        List<String> payeeIds = paymentService.getPayees(payerId);
        return ResponseEntity.ok(payeeIds);
    }
}

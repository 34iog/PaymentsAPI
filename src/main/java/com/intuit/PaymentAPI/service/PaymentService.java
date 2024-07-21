package com.intuit.PaymentAPI.service;

import com.intuit.PaymentAPI.dto.PaymentRequest;
import com.intuit.PaymentAPI.dto.PaymentResponse;
import com.intuit.PaymentAPI.dto.PaymentMethod;
import com.intuit.PaymentAPI.dto.PaymentStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaymentService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String PAYMENTS_TOPIC = "payments-topic";

    /*
    Store data to associate each payment with payees
    {
        "payerId": [ // this would be primary key in a Payments table
            {
                "payeeId": {    // secondary key in a DB
                    "amount": 70.5,
                    "currency": "USD",
                    "paymentMethodId": "8e28af1b-a3a0-43a9-96cc-57d66dd68294",
                }
            },
            ...
        ]
    }
    */
    private final Map<String, Map<String, PaymentRequest>> payments = new HashMap<>();

    /*
    Store data to associate each payment with payees
    {
        "payerId": [    // primary key in a PaymentMethod table
            "paymentMethodId": {    // secondary key in PaymentMethod table
                "type": "Credit Card",
                "Number": 123456789,
                "CVV": 111,
                "ExpirationDate": ""
            },
            ...
        ]
    }
    */
    private final Map<String, Map<String, PaymentMethod>> paymentMethods = new HashMap<>();

    public PaymentService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public PaymentResponse createPayment(PaymentRequest paymentRequest) {
        final String PAYMENT_ID = UUID.randomUUID().toString();
        // Validate the payment request
        if (paymentRequest.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }

        // Store the payment request in the nested data structure
        payments
                .computeIfAbsent(paymentRequest.getPayerId(), k -> new HashMap<>())
                .put(paymentRequest.getPayeeId(), paymentRequest);

        // Create a response
        PaymentResponse paymentResponse = new PaymentResponse(
                PAYMENT_ID,
                PaymentStatus.PENDING,  // Set to pending and return to the user immediately. Risk engine will run async and accept/deny the payment.
                "Payment created successfully and is under review."
        );

        // Publish payment to Kafka
        kafkaTemplate.send(PAYMENTS_TOPIC, paymentRequest);

        return paymentResponse;
    }

    public List<PaymentMethod> getPaymentMethods(String payerId) {
        Map<String, PaymentMethod> methods = paymentMethods.get(payerId);
        if (methods == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(methods.values());
    }

    public void createPaymentMethod(String payerId, PaymentMethod paymentMethod) {
        Map<String, PaymentMethod> methods = paymentMethods.computeIfAbsent(payerId, k -> new HashMap<>());
        methods.put(paymentMethod.getPaymentMethodId(), paymentMethod);
    }

    public void updatePaymentMethod(String payerId, String paymentMethodId, PaymentMethod paymentMethod) {
        Map<String, PaymentMethod> methods = paymentMethods.get(payerId);
        if (methods != null && methods.containsKey(paymentMethodId)) {
            methods.put(paymentMethodId, paymentMethod);
        }
    }

    public void deletePaymentMethod(String payerId, String paymentMethodId) {
        Map<String, PaymentMethod> methods = paymentMethods.get(payerId);
        if (methods != null) {
            methods.remove(paymentMethodId);
        }
    }

    public List<String> getPayees(String payerId) {
        Map<String, PaymentRequest> payeePayments = payments.get(payerId);

        // There are no payees
        if (payeePayments == null) {
            return Collections.emptyList();
        }

        return new ArrayList<>(payeePayments.keySet());
    }
}

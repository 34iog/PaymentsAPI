package com.intuit.PaymentAPI.riskengine;

import com.intuit.PaymentAPI.entity.Payment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class RiskEngine {

    private static final Random RANDOM = new Random();

    @KafkaListener(topics = "payments", groupId = "risk-engine-group")
    public void consume(Payment payment) {
        // Perform risk analysis
        boolean isPaymentApproved = RANDOM.nextDouble() < 0.7; // Approve 70% of payments
        /* TODO: implement risk logic here
        Examples:
         - Run a risk ML model based on payment inputs
         - Allow for models to be interchanged easily by targeting a model name set in
           a global config or environment variable
        */
        if (isPaymentApproved) {
            System.out.println("Payment approved");
        } else {
            System.out.println("Payment rejected");
        }
    }
}

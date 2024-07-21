# Intuit Payment API

## Questions

### Question 1
Design and implement REST API usingJVM-based language. Consider REST API design practices, response codes and bodies, error codes and bodies, naming conventions.
(Nice-to-have: Testability considerations)
- **`PaymentRequest`**: Represents a payment creation request.
- **`PaymentResponse`**: Response for a payment creation request.
- **`PaymentStatus`**: Enum for payment statuses (e.g., SUCCESS, FAILURE).
- **`PaymentMethod`**: Base class for payment methods.
- **`CreditCard`**: Represents a credit card payment method.
- **`BankAccount`**: Represents a bank account payment method.
- **`PaymentController`**: Handles payment-related HTTP requests.
- **`PaymentService`**: Manages payment and payment method logic.
- **`PaymentControllerTest`**: Tests the `PaymentController` class methods for payment and payment method operations.

### Question 2
Write code in REST API that will publish new payments into any queue of your choice like Kafka, RabbitMQ, or any other.
- [Configuration class](src/main/java/com/intuit/PaymentAPI/config/KafkaConfig.java) for setting up Kafka producers in a Spring Boot application
- createPayment method in [PaymentService.java](src/main/java/com/intuit/PaymentAPI/service/PaymentService.java) publishes the payment request to Kafka

### Question 3
Develop a proof of concept of the risk engine that will consume payment messages from the queue and perform risk analysis. For the simplicity of the exercise allow only 70% of the payments to succeed. Assume that the risk engine will be hosted in a cloud service.
(Nice-to-have: horizontal scalability requirements, build considerations such that refactoring or adding new functionality can be achieved with confidence).
- ML models like a neural net can be used to accept or deny a payment transaction. The model will output a probability or a risk score indicating the likelihood of the transaction being fraudulent. 
- Decision Making: Configure threshold for the risk score to classify transactions as high-risk or low-risk. This can be tuned to accept 70% of the transactions. We can determine the threshold with enough data. 
- These thresholds can be configured through environment variables or a global config. 
- See [design diagram](Design.drawio.png) 
- [RiskEngine.java](src/main/java/com/intuit/PaymentAPI/riskengine/RiskEngine.java) contains a simple example (ML piece to be implemented).

### Question 4
Answer theoretically: define database structure and store payments along with their risk assessment in the database of your choice.
(Nice-to-have: implement your solution)

See [design diagram](Design.drawio.png) for the database design and structure. *The database is NOT implemented in the code.* 

Database Structure

- **SQL is chosen over NoSQL because we need:**
  - Strong data consistency
  - Relationships between data (e.g., joins, indexing)
  - Structured data

- **Tables:**
  - **Primary and foreign keys indexed for faster search**

  - **Payers**
    - `payer_id` (primary key)
    - Personal info

  - **Payees**
    - `payee_id` (primary key)
    - Personal info

  - **Payments**
    - `payment_id` (primary key)
    - `payer_id` (foreign key)
    - `payee_id` (foreign key)
    - `payment_method_id` (foreign key)
    - `risk_assessment_id` (foreign key)
    - Timestamp, status, currency

  - **PaymentMethods**
    - `payment_method_id` (primary key)
    - `payer_id` (foreign key)
    - Payment type, payment info

  - **RiskAssessments**
    - `risk_assessment_id` (primary key)
    - Timestamp, status

## User Story

As a customer, I need to pay other Intuit customers for various services or products I buy from them.

## Create Payment Request

```
{
    "amount": 70.5, // type: number, description: amount to pay
    "currency": "USD", // type: string, description: payment currency
    "payerId": "e8af92bd-1910-421e-8de0-cb3dcf9bf44d", // type: string, description: Paying user unique identifier
    (GUID)
    "payeeId": "4c3e304e-ce79-4f53-bb26-4e198e6c780a", // type: string, description: Payee user unique
    identifier (GUID)
    "paymentMethodId": "8e28af1b-a3a0-43a9-96cc-57d66dd68294" // type: string, description: Payment
    Method unique identifier (GUID)
}
```

## Kafka vs RabbitMQ

- Kafka: high-throughput, distributed event streaming, and log aggregation.
- RabbitMQ: flexible and reliable message routing, moderate to high message rates, and when ease of use and management are important. 


## HTTP Error Codes 

- **400 Bad Request**: Indicates that the server cannot process the request due to client error. This can be due to invalid input or missing required fields.

- **500 Internal Server Error**: Indicates that the server encountered an unexpected condition that prevented it from fulfilling the request. This typically means that there was an error within the server or service layer.

- **200 OK**: Indicates that the request was successful and the server returned the requested data.

- **201 Created**: Indicates that the request has been fulfilled and resulted in a new resource being created.

## Database Setup

- Check if PostgreSQL is Running: `brew services list`

- Start PostgreSQL: `brew services start postgresql`

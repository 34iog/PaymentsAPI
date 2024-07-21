# Intuit Payment API

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

## Kafka
```
kafka-topics.sh --create --topic payments-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
```

## Major Classes

#### PaymentRequest
- Represents the data required to create a payment. It captures the details needed from the client to initiate a payment.

#### PaymentResponse
- Represents the response returned after attempting to create a payment. It includes the payment status, any relevant messages, and the payment ID.

#### PaymentMethod
- Represents a payment method that the user can choose from. This could include details about the method such as type and specific details (e.g., card type, bank account).

#### Payee
- Represents the user or entity to whom the payment will be made. This includes details like their ID, name, and email.

## Summary of Test Cases

#### Successful Payment Creation (testCreatePaymentSuccess)
- Verifies that a valid payment request returns a `201 Created` status and a successful response.

#### Invalid Amount (testCreatePaymentInvalidAmount)
- Tests that a payment request with a negative amount returns a `400 Bad Request` status with the appropriate error message.

#### Missing Required Fields (testCreatePaymentMissingFields)
- Checks that a request missing required fields returns a `400 Bad Request` status.

#### Service Layer Failure for Payment Creation (testCreatePaymentServiceFailure)
- Simulates a failure in the `PaymentService` and verifies that the controller returns a `500 Internal Server Error` with the appropriate message.

#### Successful Retrieval of Payment Methods (testGetPaymentMethods)
- Tests that a valid request for payment methods returns a `200 OK` status and the correct list of payment methods.

#### Successful Retrieval of Payees (testGetPayees)
- Verifies that a valid request for payees returns a `200 OK` status and the correct list of payees.

#### Service Layer Failure for Retrieval of Payment Methods (testGetPaymentMethodsServiceFailure)
- Simulates a failure in the `PaymentService` when retrieving payment methods and verifies that the controller returns a `500 Internal Server Error`.

#### Service Layer Failure for Retrieval of Payees (testGetPayeesServiceFailure)
- Simulates a failure in the `PaymentService` when retrieving payees and verifies that the controller returns a `500 Internal Server Error`.

## HTTP Error Codes 

- **400 Bad Request**: Indicates that the server cannot process the request due to client error. This can be due to invalid input or missing required fields.

- **500 Internal Server Error**: Indicates that the server encountered an unexpected condition that prevented it from fulfilling the request. This typically means that there was an error within the server or service layer.

- **200 OK**: Indicates that the request was successful and the server returned the requested data.

- **201 Created**: Indicates that the request has been fulfilled and resulted in a new resource being created.

## Database Setup

- Check if PostgreSQL is Running: `brew services list`

- Start PostgreSQL: `brew services start postgresql`

package com.intuit.PaymentAPI;
import com.intuit.PaymentAPI.dto.*;
import org.springframework.test.web.servlet.MvcResult;
import com.intuit.PaymentAPI.controller.PaymentController;
import com.intuit.PaymentAPI.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

class PaymentControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private PaymentController paymentController;

	@Mock
	private PaymentService paymentService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
	}

	@Test
	void testCreatePaymentSuccess() throws Exception {
		PaymentRequest paymentRequest = new PaymentRequest(70.5, "USD", "payerId", "payeeId", "paymentMethodId");
		PaymentResponse paymentResponse = new PaymentResponse("1234567890abcdef", PaymentStatus.SUCCESS, "Payment created successfully.");

		when(paymentService.createPayment(any(PaymentRequest.class))).thenReturn(paymentResponse);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/payments")
						.contentType("application/json")
						.content("{\"amount\":70.5,\"currency\":\"USD\",\"payerId\":\"payerId\",\"payeeId\":\"payeeId\",\"paymentMethodId\":\"paymentMethodId\"}")
				)
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.paymentId").value("1234567890abcdef"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(PaymentStatus.SUCCESS.toString()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Payment created successfully."));
	}


	@Test
	void testGetPaymentMethods() throws Exception {
		List<PaymentMethod> paymentMethods = new ArrayList<>();
		paymentMethods.add(new CreditCard("card1", "Visa", "123456789", "111", "12/25"));
		paymentMethods.add(new BankAccount("bank1", "111222333", "987654321", "Bank of America"));

		when(paymentService.getPaymentMethods("payerId")).thenReturn(paymentMethods);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/payment-methods")
						.param("payerId", "payerId"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].paymentMethodId").value("card1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].type").value("CREDIT_CARD"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Visa"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].number").value("123456789"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].cvv").value("111"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].expirationDate").value("12/25"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].paymentMethodId").value("bank1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].type").value("BANK"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].accountNumber").value("111222333"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].routingNumber").value("987654321"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].accountHolderName").value("Bank of America"))
				.andDo(print());
	}


	@Test
	void testCreatePaymentMethod() throws Exception {
		// Test creating Credit Card
		mockMvc.perform(MockMvcRequestBuilders.post("/api/payment-methods/payerId")
						.contentType("application/json")
						.content("{\"paymentMethodId\":\"card1\",\"type\":\"CREDIT_CARD\",\"name\":\"Visa\",\"number\":\"123456789\",\"cvv\":\"111\",\"expirationDate\":\"12/25\"}"))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.paymentMethodId").value("card1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("CREDIT_CARD"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Visa"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.number").value("123456789"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.cvv").value("111"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.expirationDate").value("12/25"))
				.andDo(print());

		// Test creating Bank Account
		mockMvc.perform(MockMvcRequestBuilders.post("/api/payment-methods/payerId")
						.contentType("application/json")
						.content("{\"paymentMethodId\":\"bank1\",\"type\":\"BANK\",\"accountNumber\":\"111222333\",\"routingNumber\":\"987654321\",\"accountHolderName\":\"Bank of America\"}"))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.paymentMethodId").value("bank1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("BANK"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").value("111222333"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.routingNumber").value("987654321"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.accountHolderName").value("Bank of America"))
				.andDo(print());

		// Verify the interactions with the PaymentService mock
		verify(paymentService, times(1)).createPaymentMethod(eq("payerId"), any(CreditCard.class));
		verify(paymentService, times(1)).createPaymentMethod(eq("payerId"), any(BankAccount.class));
	}


	@Test
	void testUpdatePaymentMethod() throws Exception {
		PaymentMethod creditCard = new CreditCard("card1", "Visa", "123456789", "111", "12/25");
		PaymentMethod bankAccount = new BankAccount("bank1", "111222333", "987654321", "Bank of America");

		mockMvc.perform(MockMvcRequestBuilders.put("/api/payment-methods/payerId/card1")
						.contentType("application/json")
						.content("{\"paymentMethodId\":\"card1\",\"type\":\"CREDIT_CARD\",\"name\":\"Visa\",\"number\":\"123456789\",\"cvv\":\"111\",\"expirationDate\":\"12/25\"}"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.paymentMethodId").value("card1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("CREDIT_CARD"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Visa"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.number").value("123456789"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.cvv").value("111"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.expirationDate").value("12/25"))
				.andDo(print());

		mockMvc.perform(MockMvcRequestBuilders.put("/api/payment-methods/payerId/bank1")
						.contentType("application/json")
						.content("{\"paymentMethodId\":\"bank1\",\"type\":\"BANK\",\"accountNumber\":\"111222333\",\"routingNumber\":\"987654321\",\"accountHolderName\":\"Bank of America\"}"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.paymentMethodId").value("bank1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("BANK"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").value("111222333"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.routingNumber").value("987654321"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.accountHolderName").value("Bank of America"))
				.andDo(print());

		verify(paymentService, times(1)).updatePaymentMethod(eq("payerId"), eq("card1"), any(CreditCard.class));
		verify(paymentService, times(1)).updatePaymentMethod(eq("payerId"), eq("bank1"), any(BankAccount.class));
	}


	@Test
	void testDeletePaymentMethod() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/payment-methods/payerId/card1"))
				.andExpect(MockMvcResultMatchers.status().isNoContent())
				.andDo(print());

		mockMvc.perform(MockMvcRequestBuilders.delete("/api/payment-methods/payerId/bank1"))
				.andExpect(MockMvcResultMatchers.status().isNoContent())
				.andDo(print());

		verify(paymentService, times(1)).deletePaymentMethod(eq("payerId"), eq("card1"));
		verify(paymentService, times(1)).deletePaymentMethod(eq("payerId"), eq("bank1"));
	}


	// Successful Retrieval of Payees
	@Test
	void testGetPayees() throws Exception {
		List<String> payeeIds = List.of("payee1", "payee2");

		when(paymentService.getPayees("payerId")).thenReturn(payeeIds);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/payees")
						.param("payerId", "payerId"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0]").value("payee1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1]").value("payee2"))
				.andDo(print());
	}
}

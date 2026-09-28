package com.gokul.dronelocker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gokul.dronelocker.entity.Payment;
import com.gokul.dronelocker.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin
public class PaymentController {

    private final PaymentService paymentService;

    // Constructor injection
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // 1. Record payment
    @PostMapping
    public Payment recordPayment(@RequestBody Payment payment) {
        return paymentService.recordPayment(payment);
    }

    // 2. Get all payments
    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    // 3. Get payment by ID
    @GetMapping("/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }

    // 4. Get payments by invoice
    @GetMapping("/invoice/{invoiceId}")
    public List<Payment> getPaymentsByInvoice(@PathVariable Long invoiceId) {
        return paymentService.getPaymentsByInvoice(invoiceId);
    }
}

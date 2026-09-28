package com.gokul.dronelocker.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gokul.dronelocker.entity.Invoice;
import com.gokul.dronelocker.entity.Payment;
import com.gokul.dronelocker.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceService invoiceService;

    // Constructor injection
    public PaymentService(PaymentRepository paymentRepository, InvoiceService invoiceService) {
        this.paymentRepository = paymentRepository;
        this.invoiceService = invoiceService;
    }

    // 1. Record a payment (updates invoice status if successful)
    @Transactional
    public Payment recordPayment(Payment payment) {
        if (payment.getPaymentDate() == null) {
            payment.setPaymentDate(LocalDateTime.now());
        }
        Payment savedPayment = paymentRepository.save(payment);

        // If payment is successful, mark invoice as PAID
        if ("SUCCESS".equals(payment.getStatus()) && payment.getInvoiceId() != null) {
            Invoice invoice = invoiceService.getInvoiceById(payment.getInvoiceId());
            if (invoice != null) {
                invoice.setStatus("PAID");
                invoiceService.saveInvoice(invoice);
            }
        }

        return savedPayment;
    }

    // 2. Get all payments
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // 3. Get payment by ID
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    // 4. Get payments by invoice
    public List<Payment> getPaymentsByInvoice(Long invoiceId) {
        return paymentRepository.findByInvoiceId(invoiceId);
    }
}

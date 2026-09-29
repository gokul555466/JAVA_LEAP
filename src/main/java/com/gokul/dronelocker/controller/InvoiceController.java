package com.gokul.dronelocker.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gokul.dronelocker.entity.Invoice;
import com.gokul.dronelocker.service.InvoiceService;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin
public class InvoiceController {

    private final InvoiceService invoiceService;

    // Constructor injection
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    // 1. Create invoice
    @PostMapping
    public Invoice createInvoice(@Valid @RequestBody Invoice invoice) {
        return invoiceService.createInvoice(invoice);
    }

    // 2. Get all invoices
    @GetMapping
    public List<Invoice> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    // 3. Get invoice by ID
    @GetMapping("/{id}")
    public Invoice getInvoiceById(@PathVariable Long id) {
        return invoiceService.getInvoiceById(id);
    }

    // 4. Get invoice by number
    @GetMapping("/number/{invoiceNumber}")
    public Invoice getInvoiceByNumber(@PathVariable String invoiceNumber) {
        return invoiceService.getInvoiceByNumber(invoiceNumber);
    }

    // 5. Get invoices by courier
    @GetMapping("/courier/{courierServiceId}")
    public List<Invoice> getInvoicesByCourier(@PathVariable Long courierServiceId) {
        return invoiceService.getInvoicesByCourier(courierServiceId);
    }

    // 6. Get unpaid invoices
    @GetMapping("/unpaid")
    public List<Invoice> getUnpaidInvoices() {
        return invoiceService.getUnpaidInvoices();
    }
}

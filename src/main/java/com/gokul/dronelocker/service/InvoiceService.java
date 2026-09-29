package com.gokul.dronelocker.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gokul.dronelocker.entity.Invoice;
import com.gokul.dronelocker.repository.InvoiceRepository;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    // Constructor injection
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    // 1. Create invoice
    public Invoice createInvoice(Invoice invoice) {
        trimFields(invoice);
        // Calculate total
        invoice.setTotalAmount(invoice.getLandingFee() + invoice.getStorageFee());
        if (invoice.getStatus() == null || invoice.getStatus().trim().isEmpty()) {
            invoice.setStatus("UNPAID");
        }
        if (invoice.getInvoiceDate() == null) {
            invoice.setInvoiceDate(LocalDateTime.now());
        }
        if (invoice.getDueDate() == null) {
            invoice.setDueDate(LocalDateTime.now().plusDays(30));
        }
        validateBusinessRules(invoice);
        return invoiceRepository.save(invoice);
    }

    // 2. Get all invoices
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    // 3. Get invoice by ID
    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    // 4. Get invoice by number
    public Invoice getInvoiceByNumber(String invoiceNumber) {
        return invoiceRepository.findByInvoiceNumber(invoiceNumber).orElse(null);
    }

    // 5. Get invoices by courier service
    public List<Invoice> getInvoicesByCourier(Long courierServiceId) {
        return invoiceRepository.findByCourierServiceId(courierServiceId);
    }

    // 6. Get unpaid invoices
    public List<Invoice> getUnpaidInvoices() {
        return invoiceRepository.findByStatus("UNPAID");
    }

    // 7. Calculate total
    public double calculateTotal(double landingFee, double storageFee) {
        return landingFee + storageFee;
    }

    // 8. Save invoice (used internally by payment service)
    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    private void trimFields(Invoice invoice) {
        if (invoice.getInvoiceNumber() != null) invoice.setInvoiceNumber(invoice.getInvoiceNumber().trim());
        if (invoice.getCustomerName() != null) invoice.setCustomerName(invoice.getCustomerName().trim());
        if (invoice.getPackageCode() != null) invoice.setPackageCode(invoice.getPackageCode().trim());
    }

    private void validateBusinessRules(Invoice invoice) {
        if (invoice.getDueDate() != null && invoice.getInvoiceDate() != null
                && invoice.getDueDate().isBefore(invoice.getInvoiceDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Due date cannot be before invoice date");
        }
    }
}

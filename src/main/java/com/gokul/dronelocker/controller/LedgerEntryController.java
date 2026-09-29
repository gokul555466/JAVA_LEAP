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

import com.gokul.dronelocker.entity.LedgerEntry;
import com.gokul.dronelocker.service.LedgerEntryService;

@RestController
@RequestMapping("/api/ledger")
@CrossOrigin
public class LedgerEntryController {

    private final LedgerEntryService ledgerEntryService;

    // Constructor injection
    public LedgerEntryController(LedgerEntryService ledgerEntryService) {
        this.ledgerEntryService = ledgerEntryService;
    }

    // 1. Create ledger entry
    @PostMapping
    public LedgerEntry createEntry(@Valid @RequestBody LedgerEntry entry) {
        return ledgerEntryService.createEntry(entry);
    }

    // 2. Get all ledger entries
    @GetMapping
    public List<LedgerEntry> getAllEntries() {
        return ledgerEntryService.getAllEntries();
    }

    // 3. Get entries by account name
    @GetMapping("/account/{accountName}")
    public List<LedgerEntry> getEntriesByAccount(@PathVariable String accountName) {
        return ledgerEntryService.getEntriesByAccount(accountName);
    }
}

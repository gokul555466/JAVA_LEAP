package com.gokul.dronelocker.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gokul.dronelocker.entity.LedgerEntry;
import com.gokul.dronelocker.repository.LedgerEntryRepository;

@Service
public class LedgerEntryService {

    private final LedgerEntryRepository ledgerEntryRepository;

    // Constructor injection
    public LedgerEntryService(LedgerEntryRepository ledgerEntryRepository) {
        this.ledgerEntryRepository = ledgerEntryRepository;
    }

    // 1. Create ledger entry
    public LedgerEntry createEntry(LedgerEntry entry) {
        if (entry.getTransactionDate() == null) {
            entry.setTransactionDate(LocalDateTime.now());
        }
        return ledgerEntryRepository.save(entry);
    }

    // 2. Get all ledger entries
    public List<LedgerEntry> getAllEntries() {
        return ledgerEntryRepository.findAll();
    }

    // 3. Get entries by account name
    public List<LedgerEntry> getEntriesByAccount(String accountName) {
        return ledgerEntryRepository.findByAccountName(accountName);
    }

    // 4. Get entries by account type
    public List<LedgerEntry> getEntriesByType(String accountType) {
        return ledgerEntryRepository.findByAccountType(accountType);
    }

    // 5. Record landing fee revenue
    public void recordLandingFeeRevenue(String reference, double amount) {
        LedgerEntry entry = new LedgerEntry();
        entry.setTransactionReference(reference);
        entry.setAccountName("Landing Fee Revenue");
        entry.setAccountType("INCOME");
        entry.setDebit(0);
        entry.setCredit(amount);
        entry.setDescription("Landing fee revenue from " + reference);
        entry.setTransactionDate(LocalDateTime.now());
        ledgerEntryRepository.save(entry);
    }

    // 6. Record storage fee revenue
    public void recordStorageFeeRevenue(String reference, double amount) {
        LedgerEntry entry = new LedgerEntry();
        entry.setTransactionReference(reference);
        entry.setAccountName("Locker Storage Fees");
        entry.setAccountType("INCOME");
        entry.setDebit(0);
        entry.setCredit(amount);
        entry.setDescription("Storage fee revenue from " + reference);
        entry.setTransactionDate(LocalDateTime.now());
        ledgerEntryRepository.save(entry);
    }

    // 7. Record maintenance expense
    public void recordMaintenanceExpense(String reference, double amount) {
        LedgerEntry entry = new LedgerEntry();
        entry.setTransactionReference(reference);
        entry.setAccountName("Locker Door Maintenance");
        entry.setAccountType("EXPENSE");
        entry.setDebit(amount);
        entry.setCredit(0);
        entry.setDescription("Maintenance expense: " + reference);
        entry.setTransactionDate(LocalDateTime.now());
        ledgerEntryRepository.save(entry);
    }

    // 8. Record network expense
    public void recordNetworkExpense(String reference, double amount) {
        LedgerEntry entry = new LedgerEntry();
        entry.setTransactionReference(reference);
        entry.setAccountName("Cellular Network Data Costs");
        entry.setAccountType("EXPENSE");
        entry.setDebit(amount);
        entry.setCredit(0);
        entry.setDescription("Network expense: " + reference);
        entry.setTransactionDate(LocalDateTime.now());
        ledgerEntryRepository.save(entry);
    }

    // 9. Record payment received
    public void recordPaymentReceived(String reference, double amount) {
        // Debit Cash/Bank (asset increases)
        LedgerEntry cashEntry = new LedgerEntry();
        cashEntry.setTransactionReference(reference);
        cashEntry.setAccountName("Cash/Bank");
        cashEntry.setAccountType("ASSET");
        cashEntry.setDebit(amount);
        cashEntry.setCredit(0);
        cashEntry.setDescription("Payment received: " + reference);
        cashEntry.setTransactionDate(LocalDateTime.now());
        ledgerEntryRepository.save(cashEntry);

        // Credit Customer Receivables (asset decreases)
        LedgerEntry receivableEntry = new LedgerEntry();
        receivableEntry.setTransactionReference(reference);
        receivableEntry.setAccountName("Customer Receivables");
        receivableEntry.setAccountType("ASSET");
        receivableEntry.setDebit(0);
        receivableEntry.setCredit(amount);
        receivableEntry.setDescription("Receivable cleared: " + reference);
        receivableEntry.setTransactionDate(LocalDateTime.now());
        ledgerEntryRepository.save(receivableEntry);
    }

    // 10. Get total income
    public double getTotalIncome() {
        List<LedgerEntry> incomeEntries = ledgerEntryRepository.findByAccountType("INCOME");
        return incomeEntries.stream().mapToDouble(LedgerEntry::getCredit).sum();
    }

    // 11. Get total expenses
    public double getTotalExpenses() {
        List<LedgerEntry> expenseEntries = ledgerEntryRepository.findByAccountType("EXPENSE");
        return expenseEntries.stream().mapToDouble(LedgerEntry::getDebit).sum();
    }
}

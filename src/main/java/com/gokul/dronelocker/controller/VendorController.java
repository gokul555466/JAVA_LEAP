package com.gokul.dronelocker.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gokul.dronelocker.entity.Vendor;
import com.gokul.dronelocker.service.VendorService;

@RestController
@RequestMapping("/api/vendors")
@CrossOrigin
public class VendorController {

    private final VendorService vendorService;

    // Constructor injection
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    // 1. Create vendor
    @PostMapping
    public Vendor createVendor(@Valid @RequestBody Vendor vendor) {
        return vendorService.createVendor(vendor);
    }

    // 2. Get all vendors
    @GetMapping
    public List<Vendor> getAllVendors() {
        return vendorService.getAllVendors();
    }

    // 3. Get vendor by ID
    @GetMapping("/{id}")
    public Vendor getVendorById(@PathVariable Long id) {
        return vendorService.getVendorById(id);
    }

    // 4. Update vendor
    @PutMapping("/{id}")
    public Vendor updateVendor(@PathVariable Long id, @Valid @RequestBody Vendor updatedVendor) {
        return vendorService.updateVendor(id, updatedVendor);
    }

    // 5. Delete vendor
    @DeleteMapping("/{id}")
    public String deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendor(id);
        return "Vendor deleted successfully";
    }
}

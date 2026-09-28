package com.gokul.dronelocker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gokul.dronelocker.entity.Vendor;
import com.gokul.dronelocker.repository.VendorRepository;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;

    // Constructor injection
    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    // 1. Create vendor
    public Vendor createVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    // 2. Get all vendors
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    // 3. Get vendor by ID
    public Vendor getVendorById(Long id) {
        return vendorRepository.findById(id).orElse(null);
    }

    // 4. Update vendor
    public Vendor updateVendor(Long id, Vendor updatedVendor) {
        Vendor existing = vendorRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setVendorCode(updatedVendor.getVendorCode());
            existing.setVendorName(updatedVendor.getVendorName());
            existing.setContactPerson(updatedVendor.getContactPerson());
            existing.setEmail(updatedVendor.getEmail());
            existing.setPhone(updatedVendor.getPhone());
            existing.setActive(updatedVendor.isActive());
            return vendorRepository.save(existing);
        }
        return null;
    }

    // 5. Delete vendor
    public void deleteVendor(Long id) {
        vendorRepository.deleteById(id);
    }
}

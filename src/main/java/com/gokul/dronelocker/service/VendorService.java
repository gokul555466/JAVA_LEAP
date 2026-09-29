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
        trimFields(vendor);
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
        trimFields(updatedVendor);
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

    private void trimFields(Vendor v) {
        if (v.getVendorCode() != null) v.setVendorCode(v.getVendorCode().trim());
        if (v.getVendorName() != null) v.setVendorName(v.getVendorName().trim());
        if (v.getContactPerson() != null) v.setContactPerson(v.getContactPerson().trim());
        if (v.getEmail() != null) v.setEmail(v.getEmail().trim());
        if (v.getPhone() != null) v.setPhone(v.getPhone().trim());
    }
}

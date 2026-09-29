package com.gokul.dronelocker.service;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gokul.dronelocker.entity.Package;
import com.gokul.dronelocker.repository.PackageRepository;

@Service
public class PackageService {

    private final PackageRepository packageRepository;

    // Constructor injection
    public PackageService(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    // 1. Create a package
    public Package createPackage(Package pkg) {
        trimFields(pkg);
        if (pkg.getStatus() == null || pkg.getStatus().trim().isEmpty()) {
            pkg.setStatus("REGISTERED");
        }
        return packageRepository.save(pkg);
    }

    // 2. Get all packages
    public List<Package> getAllPackages() {
        return packageRepository.findAll();
    }

    // 3. Get package by ID
    public Package getPackageById(Long id) {
        return packageRepository.findById(id).orElse(null);
    }

    // 4. Get package by code
    public Package getPackageByCode(String packageCode) {
        return packageRepository.findByPackageCode(packageCode).orElse(null);
    }

    // 5. Get packages by courier service
    public List<Package> getPackagesByCourierService(Long courierServiceId) {
        return packageRepository.findByCourierServiceId(courierServiceId);
    }

    // 6. Get packages by status
    public List<Package> getPackagesByStatus(String status) {
        return packageRepository.findByStatus(status);
    }

    // 7. Update package
    public Package updatePackage(Long id, Package updatedPkg) {
        trimFields(updatedPkg);
        Package existingPkg = packageRepository.findById(id).orElse(null);
        if (existingPkg != null) {
            existingPkg.setPackageCode(updatedPkg.getPackageCode());
            existingPkg.setRecipientName(updatedPkg.getRecipientName());
            existingPkg.setRecipientPhone(updatedPkg.getRecipientPhone());
            existingPkg.setRecipientEmail(updatedPkg.getRecipientEmail());
            existingPkg.setDeliveryAddress(updatedPkg.getDeliveryAddress());
            existingPkg.setPickupPasscode(updatedPkg.getPickupPasscode());
            existingPkg.setStatus(updatedPkg.getStatus());
            existingPkg.setCourierServiceId(updatedPkg.getCourierServiceId());
            existingPkg.setLockerPadId(updatedPkg.getLockerPadId());
            existingPkg.setLockerBayId(updatedPkg.getLockerBayId());
            existingPkg.setStorageFee(updatedPkg.getStorageFee());
            existingPkg.setReceivedAt(updatedPkg.getReceivedAt());
            existingPkg.setPickedUpAt(updatedPkg.getPickedUpAt());
            return packageRepository.save(existingPkg);
        }
        return null;
    }

    // 8. Delete package
    public void deletePackage(Long id) {
        packageRepository.deleteById(id);
    }

    // 9. Generate secure 6-digit pickup passcode
    public String generatePickupPasscode() {
        SecureRandom random = new SecureRandom();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    // 10. Save package (used internally by other services)
    public Package savePackage(Package pkg) {
        return packageRepository.save(pkg);
    }

    private void trimFields(Package pkg) {
        if (pkg.getPackageCode() != null) pkg.setPackageCode(pkg.getPackageCode().trim());
        if (pkg.getRecipientName() != null) pkg.setRecipientName(pkg.getRecipientName().trim());
        if (pkg.getRecipientPhone() != null) pkg.setRecipientPhone(pkg.getRecipientPhone().trim());
        if (pkg.getRecipientEmail() != null) pkg.setRecipientEmail(pkg.getRecipientEmail().trim());
        if (pkg.getDeliveryAddress() != null) pkg.setDeliveryAddress(pkg.getDeliveryAddress().trim());
    }
}

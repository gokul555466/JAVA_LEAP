package com.gokul.dronelocker.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.gokul.dronelocker.dto.PickupRequest;
import com.gokul.dronelocker.entity.LockerBay;
import com.gokul.dronelocker.entity.Package;
import com.gokul.dronelocker.service.LockerBayService;
import com.gokul.dronelocker.service.PackageService;

@RestController
@RequestMapping("/api/packages")
@CrossOrigin
public class PackageController {

    private final PackageService packageService;
    private final LockerBayService lockerBayService;

    // Constructor injection
    public PackageController(PackageService packageService, LockerBayService lockerBayService) {
        this.packageService = packageService;
        this.lockerBayService = lockerBayService;
    }

    // 1. Create package
    @PostMapping
    public Package createPackage(@Valid @RequestBody Package pkg) {
        return packageService.createPackage(pkg);
    }

    // 2. Get all packages
    @GetMapping
    public List<Package> getAllPackages() {
        return packageService.getAllPackages();
    }

    // 3. Get package by ID
    @GetMapping("/{id}")
    public Package getPackageById(@PathVariable Long id) {
        return packageService.getPackageById(id);
    }

    // 4. Get package by code
    @GetMapping("/code/{packageCode}")
    public Package getPackageByCode(@PathVariable String packageCode) {
        return packageService.getPackageByCode(packageCode);
    }

    // 5. Get packages by courier service
    @GetMapping("/courier/{courierServiceId}")
    public List<Package> getPackagesByCourierService(@PathVariable Long courierServiceId) {
        return packageService.getPackagesByCourierService(courierServiceId);
    }

    // 6. Get packages by status
    @GetMapping("/status/{status}")
    public List<Package> getPackagesByStatus(@PathVariable String status) {
        return packageService.getPackagesByStatus(status);
    }

    // 7. Update package
    @PutMapping("/{id}")
    public Package updatePackage(@PathVariable Long id, @Valid @RequestBody Package updatedPkg) {
        return packageService.updatePackage(id, updatedPkg);
    }

    // 8. Delete package
    @DeleteMapping("/{id}")
    public String deletePackage(@PathVariable Long id) {
        packageService.deletePackage(id);
        return "Package deleted successfully";
    }

    // 9. Pickup package with passcode verification
    @PostMapping("/{packageCode}/pickup")
    public Map<String, Object> pickupPackage(@PathVariable String packageCode,
                                              @Valid @RequestBody PickupRequest pickupRequest) {
        Map<String, Object> response = new HashMap<>();

        // Find the package
        Package pkg = packageService.getPackageByCode(packageCode);
        if (pkg == null) {
            response.put("message", "Package not found: " + packageCode);
            return response;
        }

        // Verify package is in locker
        if (!"DELIVERED_TO_LOCKER".equals(pkg.getStatus())) {
            response.put("message", "Package is not available for pickup. Current status: " + pkg.getStatus());
            return response;
        }

        // Verify passcode
        if (pkg.getPickupPasscode() == null ||
                !pkg.getPickupPasscode().equals(pickupRequest.getPasscode())) {
            response.put("message", "Invalid pickup passcode");
            return response;
        }

        // Calculate storage duration and fee
        LocalDateTime now = LocalDateTime.now();
        double storageFee = 0.0;
        if (pkg.getReceivedAt() != null) {
            long hours = Duration.between(pkg.getReceivedAt(), now).toHours();
            if (hours < 1) {
                hours = 1; // Minimum 1 hour
            }
            storageFee = hours * 10.0; // Rs 10 per hour
        }

        // Update package status
        pkg.setStatus("PICKED_UP");
        pkg.setPickedUpAt(now);
        pkg.setStorageFee(storageFee);
        packageService.savePackage(pkg);

        // Free the locker bay
        if (pkg.getLockerBayId() != null) {
            LockerBay bay = lockerBayService.getBayById(pkg.getLockerBayId());
            if (bay != null) {
                bay.setStatus("AVAILABLE");
                bay.setPackageCode(null);
                lockerBayService.updateBay(bay.getId(), bay);
            }
        }

        // Build success response
        response.put("message", "Package picked up successfully");
        response.put("packageCode", packageCode);
        response.put("storageFee", storageFee);
        response.put("pickedUpAt", now.toString());

        return response;
    }
}

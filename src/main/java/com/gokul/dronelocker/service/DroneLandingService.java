package com.gokul.dronelocker.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gokul.dronelocker.entity.DroneLanding;
import com.gokul.dronelocker.entity.LockerBay;
import com.gokul.dronelocker.entity.LockerPad;
import com.gokul.dronelocker.entity.Package;
import com.gokul.dronelocker.repository.DroneLandingRepository;

@Service
public class DroneLandingService {

    private final DroneLandingRepository droneLandingRepository;
    private final PackageService packageService;
    private final LockerBayService lockerBayService;
    private final LockerPadService lockerPadService;

    // Constructor injection
    public DroneLandingService(DroneLandingRepository droneLandingRepository,
                               PackageService packageService,
                               LockerBayService lockerBayService,
                               LockerPadService lockerPadService) {
        this.droneLandingRepository = droneLandingRepository;
        this.packageService = packageService;
        this.lockerBayService = lockerBayService;
        this.lockerPadService = lockerPadService;
    }

    // 1. Create a landing record
    public DroneLanding createLanding(DroneLanding landing) {
        return droneLandingRepository.save(landing);
    }

    // 2. Get all landings
    public List<DroneLanding> getAllLandings() {
        return droneLandingRepository.findAll();
    }

    // 3. Get landing by ID
    public DroneLanding getLandingById(Long id) {
        return droneLandingRepository.findById(id).orElse(null);
    }

    // 4. Get landings by pad
    public List<DroneLanding> getLandingsByPad(Long lockerPadId) {
        return droneLandingRepository.findByLockerPadId(lockerPadId);
    }

    // 5. Get landings by package
    public List<DroneLanding> getLandingsByPackage(String packageCode) {
        return droneLandingRepository.findByPackageCode(packageCode);
    }

    // 6. Complete a landing
    public DroneLanding completeLanding(Long id) {
        DroneLanding landing = droneLandingRepository.findById(id).orElse(null);
        if (landing != null) {
            landing.setStatus("COMPLETED");
            landing.setDepartureTime(LocalDateTime.now());
            return droneLandingRepository.save(landing);
        }
        return null;
    }

    // 7. Cancel a landing
    public DroneLanding cancelLanding(Long id) {
        DroneLanding landing = droneLandingRepository.findById(id).orElse(null);
        if (landing != null) {
            landing.setStatus("CANCELLED");
            return droneLandingRepository.save(landing);
        }
        return null;
    }

    // 8. CORE WORKFLOW — Process drone landing
    @Transactional
    public Map<String, Object> processDroneLanding(String droneCode, String packageCode, Long lockerPadId) {
        Map<String, Object> response = new HashMap<>();

        // Step 1: Find the package
        Package pkg = packageService.getPackageByCode(packageCode);
        if (pkg == null) {
            response.put("success", false);
            response.put("message", "Package not found: " + packageCode);
            return response;
        }

        // Step 2: Find the locker pad
        LockerPad pad = lockerPadService.getPadById(lockerPadId);
        if (pad == null) {
            response.put("success", false);
            response.put("message", "Locker pad not found: " + lockerPadId);
            return response;
        }

        // Step 3: Find an available bay
        LockerBay availableBay = lockerBayService.findAvailableBay(lockerPadId);
        if (availableBay == null) {
            response.put("success", false);
            response.put("message", "No available bay at pad: " + pad.getPadCode());
            return response;
        }

        // Step 4: Assign bay — mark as OCCUPIED
        availableBay.setStatus("OCCUPIED");
        availableBay.setPackageCode(packageCode);
        lockerBayService.updateBay(availableBay.getId(), availableBay);

        // Step 5: Generate pickup passcode if not already set
        if (pkg.getPickupPasscode() == null || pkg.getPickupPasscode().trim().isEmpty()) {
            String passcode = packageService.generatePickupPasscode();
            pkg.setPickupPasscode(passcode);
        }

        // Step 6: Update package
        pkg.setStatus("DELIVERED_TO_LOCKER");
        pkg.setLockerPadId(lockerPadId);
        pkg.setLockerBayId(availableBay.getId());
        pkg.setReceivedAt(LocalDateTime.now());
        packageService.savePackage(pkg);

        // Step 7: Calculate landing fee from pad tariff
        double landingFee = pad.getLandingTariff();

        // Step 8: Create drone landing record
        DroneLanding landing = new DroneLanding();
        landing.setLandingCode("LND-" + System.currentTimeMillis());
        landing.setDroneCode(droneCode);
        landing.setPackageCode(packageCode);
        landing.setLockerPadId(lockerPadId);
        landing.setLockerBayId(availableBay.getId());
        landing.setArrivalTime(LocalDateTime.now());
        landing.setLandingFee(landingFee);
        landing.setStatus("LANDED");
        droneLandingRepository.save(landing);

        // Step 9: Build success response
        response.put("success", true);
        response.put("message", "Drone landed successfully");
        response.put("landingCode", landing.getLandingCode());
        response.put("droneCode", droneCode);
        response.put("packageCode", packageCode);
        response.put("bayCode", availableBay.getBayCode());
        response.put("bayId", availableBay.getId());
        response.put("landingFee", landingFee);
        response.put("pickupPasscode", pkg.getPickupPasscode());
        response.put("status", "LANDED");

        return response;
    }
}

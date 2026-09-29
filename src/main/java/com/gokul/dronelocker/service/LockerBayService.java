package com.gokul.dronelocker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gokul.dronelocker.entity.LockerBay;
import com.gokul.dronelocker.repository.LockerBayRepository;

@Service
public class LockerBayService {

    private final LockerBayRepository lockerBayRepository;

    // Constructor injection
    public LockerBayService(LockerBayRepository lockerBayRepository) {
        this.lockerBayRepository = lockerBayRepository;
    }

    // 1. Create a bay
    public LockerBay createBay(LockerBay bay) {
        trimFields(bay);
        if (bay.getStatus() == null || bay.getStatus().trim().isEmpty()) {
            bay.setStatus("AVAILABLE");
        }
        return lockerBayRepository.save(bay);
    }

    // 2. Get all bays
    public List<LockerBay> getAllBays() {
        return lockerBayRepository.findAll();
    }

    // 3. Get bay by ID
    public LockerBay getBayById(Long id) {
        return lockerBayRepository.findById(id).orElse(null);
    }

    // 4. Get bays by LockerPad ID
    public List<LockerBay> getBaysByPadId(Long lockerPadId) {
        return lockerBayRepository.findByLockerPadId(lockerPadId);
    }

    // 5. Update bay by ID
    public LockerBay updateBay(Long id, LockerBay updatedBay) {
        trimFields(updatedBay);
        LockerBay existingBay = lockerBayRepository.findById(id).orElse(null);
        if (existingBay != null) {
            existingBay.setBayCode(updatedBay.getBayCode());
            existingBay.setStatus(updatedBay.getStatus());
            existingBay.setPackageCode(updatedBay.getPackageCode());
            existingBay.setLockerPadId(updatedBay.getLockerPadId());
            return lockerBayRepository.save(existingBay);
        }
        return null;
    }

    // 6. Delete bay by ID
    public void deleteBay(Long id) {
        lockerBayRepository.deleteById(id);
    }

    // 7. Find available bay for a specific LockerPad
    public LockerBay findAvailableBay(Long lockerPadId) {
        return lockerBayRepository.findFirstByLockerPadIdAndStatus(lockerPadId, "AVAILABLE").orElse(null);
    }

    private void trimFields(LockerBay bay) {
        if (bay.getBayCode() != null) bay.setBayCode(bay.getBayCode().trim());
        if (bay.getPackageCode() != null) bay.setPackageCode(bay.getPackageCode().trim());
    }
}

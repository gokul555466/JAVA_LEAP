package com.gokul.dronelocker.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gokul.dronelocker.entity.LockerPad;
import com.gokul.dronelocker.repository.LockerPadRepository;

@Service
public class LockerPadService {

    private final LockerPadRepository lockerPadRepository;

    // Constructor injection
    public LockerPadService(LockerPadRepository lockerPadRepository) {
        this.lockerPadRepository = lockerPadRepository;
    }

    // 1. Create a locker pad
    public LockerPad createPad(LockerPad pad) {
        trimFields(pad);
        validateBusinessRules(pad);
        return lockerPadRepository.save(pad);
    }

    // 2. Get all locker pads
    public List<LockerPad> getAllPads() {
        return lockerPadRepository.findAll();
    }

    // 3. Get locker pad by ID
    public LockerPad getPadById(Long id) {
        return lockerPadRepository.findById(id).orElse(null);
    }

    // 4. Delete locker pad by ID
    public void deletePad(Long id) {
        lockerPadRepository.deleteById(id);
    }

    private void trimFields(LockerPad pad) {
        if (pad.getPadCode() != null) pad.setPadCode(pad.getPadCode().trim());
        if (pad.getLocation() != null) pad.setLocation(pad.getLocation().trim());
    }

    private void validateBusinessRules(LockerPad pad) {
        if (pad.getAvailableBays() > pad.getTotalBays()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Available bays (" + pad.getAvailableBays() + ") cannot exceed total bays (" + pad.getTotalBays() + ")");
        }
        if (pad.getLandingTariff() > 100000) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Landing tariff seems unreasonably high. Maximum allowed is 100000.");
        }
    }
}

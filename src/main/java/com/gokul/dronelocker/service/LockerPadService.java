package com.gokul.dronelocker.service;

import java.util.List;

import org.springframework.stereotype.Service;

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
}

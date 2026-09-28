package com.gokul.dronelocker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gokul.dronelocker.entity.LockerPad;
import com.gokul.dronelocker.service.LockerPadService;

@RestController
@RequestMapping("/api/lockers/pads")
@CrossOrigin
public class LockerPadController {

    private final LockerPadService lockerPadService;

    // Constructor injection
    public LockerPadController(LockerPadService lockerPadService) {
        this.lockerPadService = lockerPadService;
    }

    // 1. Create a locker pad
    @PostMapping
    public LockerPad createPad(@RequestBody LockerPad pad) {
        return lockerPadService.createPad(pad);
    }

    // 2. Get all locker pads
    @GetMapping
    public List<LockerPad> getAllPads() {
        return lockerPadService.getAllPads();
    }

    // 3. Get locker pad by ID
    @GetMapping("/{id}")
    public LockerPad getPadById(@PathVariable Long id) {
        return lockerPadService.getPadById(id);
    }

    // 4. Delete locker pad by ID
    @DeleteMapping("/{id}")
    public String deletePad(@PathVariable Long id) {
        lockerPadService.deletePad(id);
        return "Locker pad deleted successfully";
    }
}

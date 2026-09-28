package com.gokul.dronelocker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gokul.dronelocker.entity.LockerBay;
import com.gokul.dronelocker.service.LockerBayService;

@RestController
@RequestMapping("/api/lockers/bays")
@CrossOrigin
public class LockerBayController {

    private final LockerBayService lockerBayService;

    // Constructor injection
    public LockerBayController(LockerBayService lockerBayService) {
        this.lockerBayService = lockerBayService;
    }

    // 1. Create bay
    @PostMapping
    public LockerBay createBay(@RequestBody LockerBay bay) {
        return lockerBayService.createBay(bay);
    }

    // 2. Get all bays
    @GetMapping
    public List<LockerBay> getAllBays() {
        return lockerBayService.getAllBays();
    }

    // 3. Get bay by ID
    @GetMapping("/{id}")
    public LockerBay getBayById(@PathVariable Long id) {
        return lockerBayService.getBayById(id);
    }

    // 4. Get bays by LockerPad ID
    @GetMapping("/pad/{lockerPadId}")
    public List<LockerBay> getBaysByPadId(@PathVariable Long lockerPadId) {
        return lockerBayService.getBaysByPadId(lockerPadId);
    }

    // 5. Update bay by ID
    @PutMapping("/{id}")
    public LockerBay updateBay(@PathVariable Long id, @RequestBody LockerBay updatedBay) {
        return lockerBayService.updateBay(id, updatedBay);
    }

    // 6. Delete bay by ID
    @DeleteMapping("/{id}")
    public String deleteBay(@PathVariable Long id) {
        lockerBayService.deleteBay(id);
        return "Locker bay deleted successfully";
    }

    // 7. Find available bay for a specific LockerPad
    @GetMapping("/available/{lockerPadId}")
    public LockerBay findAvailableBay(@PathVariable Long lockerPadId) {
        return lockerBayService.findAvailableBay(lockerPadId);
    }
}

package com.gokul.dronelocker.controller;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gokul.dronelocker.entity.DroneLanding;
import com.gokul.dronelocker.dto.DroneLandingRequest;
import com.gokul.dronelocker.service.DroneLandingService;

@RestController
@RequestMapping("/api/lockers/landings")
@CrossOrigin
public class DroneLandingController {

    private final DroneLandingService droneLandingService;

    // Constructor injection
    public DroneLandingController(DroneLandingService droneLandingService) {
        this.droneLandingService = droneLandingService;
    }

    // 1. Process drone landing (core workflow)
    @PostMapping("/process")
    public Map<String, Object> processDroneLanding(@Valid @RequestBody DroneLandingRequest request) {
        return droneLandingService.processDroneLanding(
                request.getDroneCode(), request.getPackageCode(), request.getLockerPadId());
    }

    // 2. Get all landings
    @GetMapping
    public List<DroneLanding> getAllLandings() {
        return droneLandingService.getAllLandings();
    }

    // 3. Get landing by ID
    @GetMapping("/{id}")
    public DroneLanding getLandingById(@PathVariable Long id) {
        return droneLandingService.getLandingById(id);
    }

    // 4. Get landings by pad
    @GetMapping("/pad/{lockerPadId}")
    public List<DroneLanding> getLandingsByPad(@PathVariable Long lockerPadId) {
        return droneLandingService.getLandingsByPad(lockerPadId);
    }

    // 5. Get landings by package
    @GetMapping("/package/{packageCode}")
    public List<DroneLanding> getLandingsByPackage(@PathVariable String packageCode) {
        return droneLandingService.getLandingsByPackage(packageCode);
    }
}

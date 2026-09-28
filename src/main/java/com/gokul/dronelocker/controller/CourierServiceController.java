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

import com.gokul.dronelocker.entity.CourierService;
import com.gokul.dronelocker.service.CourierServiceManager;

@RestController
@RequestMapping("/api/courier-services")
@CrossOrigin
public class CourierServiceController {

    private final CourierServiceManager courierServiceManager;

    // Constructor injection
    public CourierServiceController(CourierServiceManager courierServiceManager) {
        this.courierServiceManager = courierServiceManager;
    }

    // 1. Create a courier service
    @PostMapping
    public CourierService createCourierService(@RequestBody CourierService courierService) {
        return courierServiceManager.createCourierService(courierService);
    }

    // 2. Get all courier services
    @GetMapping
    public List<CourierService> getAllCourierServices() {
        return courierServiceManager.getAllCourierServices();
    }

    // 3. Get active courier services
    @GetMapping("/active")
    public List<CourierService> getActiveCourierServices() {
        return courierServiceManager.getActiveCourierServices();
    }

    // 4. Get courier service by service code
    @GetMapping("/code/{serviceCode}")
    public CourierService getCourierServiceByCode(@PathVariable String serviceCode) {
        return courierServiceManager.getCourierServiceByCode(serviceCode);
    }

    // 5. Get courier service by ID
    @GetMapping("/{id}")
    public CourierService getCourierServiceById(@PathVariable Long id) {
        return courierServiceManager.getCourierServiceById(id);
    }

    // 6. Update courier service by ID
    @PutMapping("/{id}")
    public CourierService updateCourierService(@PathVariable Long id, @RequestBody CourierService updatedCourierService) {
        return courierServiceManager.updateCourierService(id, updatedCourierService);
    }

    // 7. Delete courier service by ID
    @DeleteMapping("/{id}")
    public String deleteCourierService(@PathVariable Long id) {
        courierServiceManager.deleteCourierService(id);
        return "Courier service deleted successfully";
    }
}

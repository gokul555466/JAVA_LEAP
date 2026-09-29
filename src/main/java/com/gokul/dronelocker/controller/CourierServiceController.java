package com.gokul.dronelocker.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
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
    public CourierService createCourierService(@Valid @RequestBody CourierService courierService) {
        if (courierService == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Courier service request body is required");
        }
        validateCourierServiceInput(courierService);
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
        if (serviceCode == null || serviceCode.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Service code must not be blank");
        }
        CourierService courierService = courierServiceManager.getCourierServiceByCode(serviceCode);
        if (courierService == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Courier service not found");
        }
        return courierService;
    }

    // 5. Get courier service by ID
    @GetMapping("/{id}")
    public CourierService getCourierServiceById(@PathVariable Long id) {
        validateId(id);
        CourierService courierService = courierServiceManager.getCourierServiceById(id);
        if (courierService == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Courier service not found");
        }
        return courierService;
    }

    // 6. Update courier service by ID
    @PutMapping("/{id}")
    public CourierService updateCourierService(@PathVariable Long id, @Valid @RequestBody CourierService updatedCourierService) {
        validateId(id);
        if (updatedCourierService == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Courier service request body is required");
        }
        validateCourierServiceInput(updatedCourierService);
        CourierService courierService = courierServiceManager.updateCourierService(id, updatedCourierService);
        if (courierService == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Courier service not found");
        }
        return courierService;
    }

    // 7. Delete courier service by ID
    @DeleteMapping("/{id}")
    public String deleteCourierService(@PathVariable Long id) {
        validateId(id);
        if (courierServiceManager.getCourierServiceById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Courier service not found");
        }
        courierServiceManager.deleteCourierService(id);
        return "Courier service deleted successfully";
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID must be a positive number");
        }
    }

    private void validateCourierServiceInput(CourierService courierService) {
        if (courierService.getServiceCode() == null || courierService.getServiceCode().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Service code must not be blank");
        }
        if (courierService.getCompanyName() == null || courierService.getCompanyName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company name must not be blank");
        }
    }
}

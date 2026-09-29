package com.gokul.dronelocker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gokul.dronelocker.entity.CourierService;
import com.gokul.dronelocker.repository.CourierServiceRepository;

@Service
public class CourierServiceManager {

    private final CourierServiceRepository courierServiceRepository;

    // Constructor injection
    public CourierServiceManager(CourierServiceRepository courierServiceRepository) {
        this.courierServiceRepository = courierServiceRepository;
    }

    // 1. Create a courier service
    public CourierService createCourierService(CourierService courierService) {
        trimFields(courierService);
        return courierServiceRepository.save(courierService);
    }

    // 2. Get all courier services
    public List<CourierService> getAllCourierServices() {
        return courierServiceRepository.findAll();
    }

    // 3. Get courier service by ID
    public CourierService getCourierServiceById(Long id) {
        return courierServiceRepository.findById(id).orElse(null);
    }

    // 4. Get courier service by service code
    public CourierService getCourierServiceByCode(String serviceCode) {
        return courierServiceRepository.findByServiceCode(serviceCode).orElse(null);
    }

    // 5. Get all active courier services
    public List<CourierService> getActiveCourierServices() {
        return courierServiceRepository.findByActiveTrue();
    }

    // 6. Update courier service by ID
    public CourierService updateCourierService(Long id, CourierService updatedCourierService) {
        trimFields(updatedCourierService);
        CourierService existingService = courierServiceRepository.findById(id).orElse(null);
        if (existingService != null) {
            existingService.setServiceCode(updatedCourierService.getServiceCode());
            existingService.setCompanyName(updatedCourierService.getCompanyName());
            existingService.setContactPerson(updatedCourierService.getContactPerson());
            existingService.setEmail(updatedCourierService.getEmail());
            existingService.setPhone(updatedCourierService.getPhone());
            existingService.setAddress(updatedCourierService.getAddress());
            existingService.setActive(updatedCourierService.isActive());
            return courierServiceRepository.save(existingService);
        }
        return null;
    }

    // 7. Delete courier service by ID
    public void deleteCourierService(Long id) {
        courierServiceRepository.deleteById(id);
    }

    private void trimFields(CourierService cs) {
        if (cs.getServiceCode() != null) cs.setServiceCode(cs.getServiceCode().trim());
        if (cs.getCompanyName() != null) cs.setCompanyName(cs.getCompanyName().trim());
        if (cs.getContactPerson() != null) cs.setContactPerson(cs.getContactPerson().trim());
        if (cs.getEmail() != null) cs.setEmail(cs.getEmail().trim());
        if (cs.getPhone() != null) cs.setPhone(cs.getPhone().trim());
        if (cs.getAddress() != null) cs.setAddress(cs.getAddress().trim());
    }
}

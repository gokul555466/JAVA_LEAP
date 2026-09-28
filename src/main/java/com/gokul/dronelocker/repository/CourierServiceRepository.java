package com.gokul.dronelocker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gokul.dronelocker.entity.CourierService;

@Repository
public interface CourierServiceRepository extends JpaRepository<CourierService, Long> {

    Optional<CourierService> findByServiceCode(String serviceCode);

    List<CourierService> findByActiveTrue();
}

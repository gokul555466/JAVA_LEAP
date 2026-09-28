package com.gokul.dronelocker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gokul.dronelocker.entity.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
}

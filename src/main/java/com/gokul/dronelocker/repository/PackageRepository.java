package com.gokul.dronelocker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gokul.dronelocker.entity.Package;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {

    Optional<Package> findByPackageCode(String packageCode);

    List<Package> findByCourierServiceId(Long courierServiceId);

    List<Package> findByStatus(String status);
}

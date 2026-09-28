package com.gokul.dronelocker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gokul.dronelocker.entity.DroneLanding;

@Repository
public interface DroneLandingRepository extends JpaRepository<DroneLanding, Long> {

    List<DroneLanding> findByLockerPadId(Long lockerPadId);

    List<DroneLanding> findByPackageCode(String packageCode);

    List<DroneLanding> findByStatus(String status);
}

package com.gokul.dronelocker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gokul.dronelocker.entity.LockerBay;

@Repository
public interface LockerBayRepository extends JpaRepository<LockerBay, Long> {

    List<LockerBay> findByLockerPadId(Long lockerPadId);

    Optional<LockerBay> findFirstByLockerPadIdAndStatus(Long lockerPadId, String status);
}

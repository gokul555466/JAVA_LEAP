package com.gokul.dronelocker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gokul.dronelocker.entity.LockerPad;

@Repository
public interface LockerPadRepository extends JpaRepository<LockerPad, Long> {
}

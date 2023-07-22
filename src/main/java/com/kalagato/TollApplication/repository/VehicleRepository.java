package com.kalagato.TollApplication.repository;

import com.kalagato.TollApplication.entity.Vehicle;
import com.kalagato.TollApplication.enums.VType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByRegistrationAndVehicleType_Type(String registration, VType type);
}

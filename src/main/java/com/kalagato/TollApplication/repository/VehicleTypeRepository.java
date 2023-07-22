package com.kalagato.TollApplication.repository;

import com.kalagato.TollApplication.entity.VehicleType;
import com.kalagato.TollApplication.enums.VType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {
    Optional<VehicleType> findByType(VType type);

}

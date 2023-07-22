package com.kalagato.TollApplication.repository;

import com.kalagato.TollApplication.entity.PassCharge;
import com.kalagato.TollApplication.entity.VehicleType;
import com.kalagato.TollApplication.enums.PassType;
import com.kalagato.TollApplication.enums.VType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassChargeRepository extends JpaRepository<PassCharge, Long> {
    Optional<PassCharge> findByVehicleType_TypeAndPass_Type(VType type, PassType type1);

    List<PassCharge> findByVehicleType_Type(VType type);
}

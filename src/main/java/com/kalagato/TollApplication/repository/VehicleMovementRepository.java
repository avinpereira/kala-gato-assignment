package com.kalagato.TollApplication.repository;

import com.kalagato.TollApplication.entity.Vehicle;
import com.kalagato.TollApplication.entity.VehicleMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VehicleMovementRepository extends JpaRepository<VehicleMovement, Long> {
    List<VehicleMovement> findByIsActiveAndEndsAtBefore(Boolean isActive, LocalDateTime endsAt);
    List<VehicleMovement> findByVehicleAndIsActive(Vehicle vehicle, Boolean isActive);

    @Query(nativeQuery = true,
    value = "SELECT toll_booth_id, max(pc.charge)\n" +
            "FROM vehicle_movement vm join pass_charge pc on vm.pass_charge_id=pc.id\n" +
            "GROUP BY toll_booth_id")
    List<Object[]> getTollBoothLeadersByCharge();

    @Query(nativeQuery = true,
            value = "SELECT toll_booth_id, MAX(number_of_visits) AS max_visits\n" +
                    "FROM vehicle_movement\n" +
                    "GROUP BY toll_booth_id\n" +
                    "ORDER BY max_visits DESC")
    List<Object[]> getTollBoothLeadersByVisits();
}
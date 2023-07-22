package com.kalagato.TollApplication.crons;

import com.kalagato.TollApplication.entity.VehicleMovement;
import com.kalagato.TollApplication.repository.VehicleMovementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@EnableScheduling
@Component
@Slf4j
public class DeactivateMovementsScheduler {
    @Autowired
    private VehicleMovementRepository vehicleMovementRepository;

    @Scheduled(fixedRate = 300000) // Run every 5 minutes (300,000 milliseconds)
    @Transactional
    public void cleanUp() {
        vehicleMovementRepository.findByIsActiveAndEndsAtBefore(true, LocalDateTime.now())
                .forEach(vm -> {
                    log.info("Deactivating Vehicle Movement for Vehicle {} at Toll {}", vm.getId(), vm.getTollBooth().getParentToll().getName());
                    vm.setIsActive(false);
                    vm.setEndsAt(LocalDateTime.now());
                    vehicleMovementRepository.save(vm);
                });
    }
}

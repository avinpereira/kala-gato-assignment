package com.kalagato.TollApplication.loaders;

import com.kalagato.TollApplication.entity.Pass;
import com.kalagato.TollApplication.entity.PassCharge;
import com.kalagato.TollApplication.entity.VehicleType;
import com.kalagato.TollApplication.enums.PassType;
import com.kalagato.TollApplication.enums.VType;
import com.kalagato.TollApplication.repository.PassChargeRepository;
import com.kalagato.TollApplication.repository.PassRepository;
import com.kalagato.TollApplication.repository.VehicleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(1)
public class NecessaryDataLoader implements CommandLineRunner {
    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;
    @Autowired
    private PassRepository passRepository;

    @Autowired
    private PassChargeRepository passChargeRepository;
    @Override
    public void run(String... args) throws Exception {
        VehicleType twoWheeler = vehicleTypeRepository.save(VehicleType.builder()
                .type(VType.TWO_WHEELER)
                .build());
        VehicleType fourWheeler = vehicleTypeRepository.save(VehicleType.builder()
                .type(VType.FOUR_WHEELER)
                .build());

        Pass singlePass = passRepository.save(Pass.builder().type(PassType.SINGLE).id(1L).build());
        Pass returnPass = passRepository.save(Pass.builder().type(PassType.RETURN).id(2L).build());
        Pass sevenDayPass = passRepository.save(Pass.builder().type(PassType.SEVEN_DAY).id(3L).build());

        List<VehicleType> vehicleTypes = List.of(twoWheeler, fourWheeler);
        List<Pass> passTypes = List.of(singlePass, returnPass, sevenDayPass);
        vehicleTypes.forEach(vt -> {
            passTypes.forEach(pt -> {
                PassCharge.PassChargeBuilder passChargeBuilder = PassCharge.builder()
                        .vehicleType(vt)
                        .pass(pt);
                if(vt.getType().equals(VType.TWO_WHEELER) && pt.getType().equals(PassType.SINGLE)){
                    passChargeBuilder.charge(Double.valueOf("50"));
                } else if (vt.getType().equals(VType.TWO_WHEELER) && pt.getType().equals(PassType.RETURN)) {
                    passChargeBuilder.charge(Double.valueOf("100"));
                }
                else if (vt.getType().equals(VType.TWO_WHEELER) && pt.getType().equals(PassType.SEVEN_DAY)) {
                    passChargeBuilder.charge(Double.valueOf("1000"));
                }
                else if (vt.getType().equals(VType.FOUR_WHEELER) && pt.getType().equals(PassType.SINGLE)) {
                    passChargeBuilder.charge(Double.valueOf("100"));
                }
                else if (vt.getType().equals(VType.FOUR_WHEELER) && pt.getType().equals(PassType.RETURN)) {
                    passChargeBuilder.charge(Double.valueOf("200"));
                }else{
                    passChargeBuilder.charge(Double.valueOf("2000"));
                }
                passChargeRepository.save(passChargeBuilder.build());
            });
        });
    }
}

package com.kalagato.TollApplication.dto;

import com.kalagato.TollApplication.entity.VehicleType;
import com.kalagato.TollApplication.enums.PassType;
import com.kalagato.TollApplication.enums.VType;
import lombok.Data;

@Data
public class MovementRequest {
    private String vehicleRegistration;
    private Long tollBoothId;
    private VType vehicleType;
    private PassType passType;
}

package com.kalagato.TollApplication.dto;

import com.kalagato.TollApplication.enums.PassType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder(toBuilder = true)
public class MovementResponse {
    private Boolean passExists;
    private LocalDateTime passValidTill;
    private Map<PassType, Double> purchasablePasses;
}

package com.kalagato.TollApplication.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class ChargeLeaderResponse {
    private BigInteger tollBoothId;
    private Double totalChargeBilled;
}

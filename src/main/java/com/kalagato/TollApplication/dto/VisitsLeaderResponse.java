package com.kalagato.TollApplication.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class VisitsLeaderResponse {
    private BigInteger tollBoothId;
    private Integer totalVisits;
}

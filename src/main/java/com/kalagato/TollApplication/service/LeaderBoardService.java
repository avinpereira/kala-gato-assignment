package com.kalagato.TollApplication.service;

import com.kalagato.TollApplication.dto.ChargeLeaderResponse;
import com.kalagato.TollApplication.dto.VisitsLeaderResponse;
import com.kalagato.TollApplication.entity.VehicleMovement;
import com.kalagato.TollApplication.repository.VehicleMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class LeaderBoardService {

    @Autowired
    private VehicleMovementRepository vehicleMovementRepository;
    public List<ChargeLeaderResponse> getMaxChargesPerToll() {
        List<Object[]> tollBoothLeadersByCharge = vehicleMovementRepository.getTollBoothLeadersByCharge();
        return mapToChargeLeaderResponse(tollBoothLeadersByCharge);
    }

    private List<ChargeLeaderResponse> mapToChargeLeaderResponse(List<Object[]> results) {
        List<ChargeLeaderResponse> responseList = new ArrayList<>();
        for (Object[] result : results) {
            ChargeLeaderResponse response = new ChargeLeaderResponse();
            response.setTollBoothId((BigInteger) result[0]);
            response.setTotalChargeBilled((Double) result[1]);
            responseList.add(response);
        }
        return responseList;
    }

    public List<VisitsLeaderResponse> getMaxVisitsPerToll() {
        List<Object[]> tollBoothLeadersByVisits = vehicleMovementRepository.getTollBoothLeadersByVisits();
        return mapToVisitsLeaderResponse(tollBoothLeadersByVisits);
    }

    private List<VisitsLeaderResponse> mapToVisitsLeaderResponse(List<Object[]> results) {
        List<VisitsLeaderResponse> responseList = new ArrayList<>();
        for (Object[] result : results) {
            VisitsLeaderResponse response = new VisitsLeaderResponse();
            response.setTollBoothId((BigInteger) result[0]);
            response.setTotalVisits((Integer) result[1]);
            responseList.add(response);
        }
        return responseList;
    }
}

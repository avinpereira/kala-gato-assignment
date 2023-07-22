package com.kalagato.TollApplication.controller;

import com.kalagato.TollApplication.dto.ChargeLeaderResponse;
import com.kalagato.TollApplication.dto.VisitsLeaderResponse;
import com.kalagato.TollApplication.service.LeaderBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class LeaderBoardController {
    @Autowired
    LeaderBoardService leaderBoardService;

    @GetMapping("/toll_booths/charges")
    public ResponseEntity<List<ChargeLeaderResponse>> getLeaderBoardForMaxCharges(){
        return ResponseEntity.ok(leaderBoardService.getMaxChargesPerToll());
    }

    @GetMapping("/toll_booths/visits")
    public ResponseEntity<List<VisitsLeaderResponse>> getLeaderBoardForMaxVisits(){
        return ResponseEntity.ok(leaderBoardService.getMaxVisitsPerToll());
    }
}

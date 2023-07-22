package com.kalagato.TollApplication.controller;

import com.kalagato.TollApplication.dto.MovementRequest;
import com.kalagato.TollApplication.dto.MovementResponse;
import com.kalagato.TollApplication.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/movements")
public class MovementController {
    @Autowired
    private MovementService movementService;

    @PostMapping("/status")
    public ResponseEntity<MovementResponse> getVehicleStatusForToll(@RequestBody MovementRequest requestBody){
        return ResponseEntity.ok(movementService.getStatus(requestBody));
    }

    @PostMapping
    public ResponseEntity<MovementResponse> registerMovement(@RequestBody MovementRequest request){
        return ResponseEntity.ok(movementService.registerMovement(request));
    }
}

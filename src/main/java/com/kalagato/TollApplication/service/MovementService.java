package com.kalagato.TollApplication.service;

import com.kalagato.TollApplication.dto.MovementRequest;
import com.kalagato.TollApplication.dto.MovementResponse;
import com.kalagato.TollApplication.entity.*;
import com.kalagato.TollApplication.enums.PassType;
import com.kalagato.TollApplication.enums.VType;
import com.kalagato.TollApplication.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovementService {

    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    VehicleMovementRepository vehicleMovementRepository;
    @Autowired
    TollBoothRepository tollBoothRepository;

    @Autowired
    PassChargeRepository passChargeRepository;
    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    public MovementResponse getStatus(MovementRequest requestBody) {
        if (requestBody == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please Provide Vehicle & Toll Information");
        }
        // Find out if Vehicle is registered in the system
        Optional<Vehicle> optionalVehicle = vehicleRepository.findByRegistrationAndVehicleType_Type(
                requestBody.getVehicleRegistration(),
                requestBody.getVehicleType());
        // Find out if Toll Booth Provided in request is valid
        TollBooth tollBooth = tollBoothRepository.findById(requestBody.getTollBoothId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Toll Booth is not registered in the system"));
        // Get the Parent Toll for this booth
        Toll parentToll = tollBooth.getParentToll();
        // Vehicle exists in system and has an active pass for this Toll; we let the vehicle through
        if (optionalVehicle.isPresent()) {
            Vehicle vehicle = optionalVehicle.get();
            List<VehicleMovement> activeVehicleMovements = vehicleMovementRepository.findByVehicleAndIsActive(vehicle, true);
            Optional<VehicleMovement> optionalVehicleMovement = activeVehicleMovements.stream()
                    .filter(vm -> vm.getTollBooth().getParentToll().equals(parentToll))
                    .findFirst();
            // If there is an Active Movement for this Particular Toll i.e (RETURN/SEVEN_DAY)
            if (optionalVehicleMovement.isPresent()) {
                VehicleMovement vehicleMovement = optionalVehicleMovement.get();
                vehicleMovement.setNumberOfVisits(vehicleMovement.getNumberOfVisits() + 1);
                // A RETURN pass can be valid for 1 day or 2 visits
                if(vehicleMovement.getPassCharge().getPass().getType().equals(PassType.RETURN)){
                    vehicleMovement.setEndsAt(LocalDateTime.now());
                    vehicleMovement.setIsActive(false);
                }
                vehicleMovement = vehicleMovementRepository.save(vehicleMovement);
                return MovementResponse.builder()
                        .passExists(true)
                        .passValidTill(vehicleMovement.getEndsAt())
                        .build();
            }
        }

        // If Vehicle is Not registered, return List of Purchasable Passes
        return MovementResponse.builder()
                .passExists(false)
                .purchasablePasses(getPurchasablePasses(requestBody.getVehicleType()))
                .build();
    }

    private Map<PassType, Double> getPurchasablePasses(VType vehicleType) {
        List<PassCharge> passCharges = passChargeRepository.findByVehicleType_Type(vehicleType);
        return passCharges.stream()
                .collect(Collectors.toMap((pc -> pc.getPass().getType()), PassCharge::getCharge));
    }

    public MovementResponse registerMovement(MovementRequest request) {
        // Vague Validation of the Request
        if (request == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please Provide Vehicle & Toll Information");
        }
        // Find out if Vehicle is registered in the system
        Vehicle vehicle = vehicleRepository.findByRegistrationAndVehicleType_Type(
                        request.getVehicleRegistration(),
                        request.getVehicleType())
                .orElseGet(() -> { // Register a new vehicle
                    VehicleType vehicleType = vehicleTypeRepository.findByType(request.getVehicleType())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vehicle Type Not Supported"));
                    return vehicleRepository.save(Vehicle.builder()
                            .registration(request.getVehicleRegistration())
                            .vehicleType(vehicleType)
                            .build());
                });
        // Find out if Toll Booth Provided in request is valid
        TollBooth tollBooth = tollBoothRepository.findById(request.getTollBoothId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Toll Booth is not registered in the system"));
        PassType passType = request.getPassType();
        PassCharge passCharge = passChargeRepository.findByVehicleType_TypeAndPass_Type(request.getVehicleType(), request.getPassType())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vehicle Type and Pass Type Charges not found"));

        //Create A New Vehicle Movement and fix endTime based on Pass Type
        VehicleMovement.VehicleMovementBuilder vehicleMovementBuilder = VehicleMovement.builder()
                .vehicle(vehicle)
                .passCharge(passCharge)
                .tollBooth(tollBooth)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .endsAt(LocalDateTime.now().plus(passType.getDurationAddOn()))
                .numberOfVisits(1);

        // A Single Pass, allows only 1 visit, therefore it is inactive after creation
        if(passType.equals(PassType.SINGLE)){
            vehicleMovementBuilder.isActive(false);
        }
        VehicleMovement vehicleMovement = vehicleMovementRepository.save(vehicleMovementBuilder.build());
        return MovementResponse.builder().passExists(true)
                .passValidTill(vehicleMovement.getEndsAt())
                .build();
    }
}

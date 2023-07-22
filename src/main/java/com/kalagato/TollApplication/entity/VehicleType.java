package com.kalagato.TollApplication.entity;

import com.kalagato.TollApplication.enums.VType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class VehicleType {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private VType type;
    @OneToMany(mappedBy = "vehicleType")
    private List<Vehicle> vehicles;
    @OneToMany(mappedBy = "vehicleType")
    private List<PassCharge> passCharges;
}

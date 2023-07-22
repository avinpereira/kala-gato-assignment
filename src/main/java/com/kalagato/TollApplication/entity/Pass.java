package com.kalagato.TollApplication.entity;

import com.kalagato.TollApplication.enums.PassType;
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
public class Pass {
    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    private PassType type;
    @OneToMany(mappedBy = "pass")
    private List<PassCharge> passCharges;
}

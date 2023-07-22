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
public class Toll {
    @Id
    private Long id;
    private String name;
    @OneToMany(mappedBy = "parentToll", cascade = CascadeType.ALL)
    List<TollBooth> tollBooths;
}

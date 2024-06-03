package com.gotruck.truckcategoryservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "truck_categories")
public class TruckCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "max_load_capacity")
    private BigDecimal maxLoadCapacity;

    @NotNull
    @Column(name = "cargo_area_width")
    private BigDecimal cargoAreaWidth;

    @NotNull
    @Column(name = "cargo_area_length")
    private BigDecimal cargoAreaLength;

    @NotNull
    @Column(name = "cargo_area_height")
    private BigDecimal cargoAreaHeight;

    @Column(name = "cargo_cubic_volume")
    private BigDecimal cargoCubicVolume;

//    @NotNull
    @Column(name = "truck_name_id")
    private Long truckNameId;
}



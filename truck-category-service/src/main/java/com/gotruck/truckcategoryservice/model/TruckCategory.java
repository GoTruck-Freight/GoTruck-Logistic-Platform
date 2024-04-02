package com.gotruck.truckcategoryservice.model;

import com.gotruck.truckcategoryservice.dto.TruckNameDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "truck-categories")
public class TruckCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @NotNull(message = "Max load capacity must not be null")
    @Positive(message = "Max load capacity must be positive")
    private double maxLoadCapacity;

    @NotNull(message = "Cargo area width must not be null")
    @Positive(message = "Cargo area width must be positive")
    private double cargoAreaWidth;

    @NotNull(message = "Cargo area length must not be null")
    @Positive(message = "Cargo area length must be positive")
    private double cargoAreaLength;

    @NotNull(message = "Cargo area height must not be null")
    @Positive(message = "Cargo area height must be positive")
    private double cargoAreaHeight;

    @Positive(message = "Cargo cubic volume must be positive")
    private double cargoCubicVolume;

    private String truckNameId;
    private TruckNameDTO truckName;
}
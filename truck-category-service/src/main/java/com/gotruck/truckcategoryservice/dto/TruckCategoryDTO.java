package com.gotruck.truckcategoryservice.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class TruckCategoryDTO {
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

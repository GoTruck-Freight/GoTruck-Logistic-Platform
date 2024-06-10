package com.gotruck.truckcategoryservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TruckCategoryDTO {
    private Long id;

    private String description;

    @NotNull(message = "Max load capacity must not be null")
    @Positive(message = "Max load capacity must be positive")
    private BigDecimal maxLoadCapacity;

    @NotNull(message = "Cargo area width must not be null")
    @Positive(message = "Cargo area width must be positive")
    private BigDecimal cargoAreaWidth;

    @NotNull(message = "Cargo area length must not be null")
    @Positive(message = "Cargo area length must be positive")
    private BigDecimal cargoAreaLength;

    @NotNull(message = "Cargo area height must not be null")
    @Positive(message = "Cargo area height must be positive")
    private BigDecimal cargoAreaHeight;

    @Positive(message = "Cargo cubic volume must be positive")
    private BigDecimal cargoCubicVolume;

//    @NotNull(message = "Truck name must not be null")
    private Long truckNameId;

}

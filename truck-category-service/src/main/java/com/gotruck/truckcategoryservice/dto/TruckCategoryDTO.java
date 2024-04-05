package com.gotruck.truckcategoryservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TruckCategoryDTO {
    private Long id;
    private String description;
    private double maxLoadCapacity;
    private double cargoAreaWidth;
    private double cargoAreaLength;
    private double cargoAreaHeight;
    private double cargoCubicVolume;

    private Long truckNameId;

    public TruckCategoryDTO(Long id, String description, double maxLoadCapacity,
                            double cargoAreaWidth, double cargoAreaLength,
                            double cargoAreaHeight, double cargoCubicVolume, Long
                            truckNameId) {
        this.id = id;
        this.description = description;
        this.maxLoadCapacity = maxLoadCapacity;
        this.cargoAreaWidth = cargoAreaWidth;
        this.cargoAreaLength = cargoAreaLength;
        this.cargoAreaHeight = cargoAreaHeight;
        this.cargoCubicVolume = cargoCubicVolume;
        this.truckNameId = truckNameId;

    }

    public TruckCategoryDTO() {

    }

}

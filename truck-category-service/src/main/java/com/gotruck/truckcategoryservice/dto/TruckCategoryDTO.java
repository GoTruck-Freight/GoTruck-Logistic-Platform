package com.gotruck.truckcategoryservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class TruckCategoryDTO {
    private Long id;
    private String description;
    private double maxLoadCapacity;
    private double cargoAreaWidth;
    private double cargoAreaLength;
    private double cargoAreaHeight;
    private double cargoCubicVolume;

    private Long truckNameId;

}

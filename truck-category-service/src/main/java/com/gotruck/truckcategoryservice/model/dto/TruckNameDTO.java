package com.gotruck.truckcategoryservice.model.dto;

import jakarta.persistence.Column;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TruckNameDTO {

    private Long id;

    @Column(nullable = false) // Enforce uniqueness and not null
    private String name;
}
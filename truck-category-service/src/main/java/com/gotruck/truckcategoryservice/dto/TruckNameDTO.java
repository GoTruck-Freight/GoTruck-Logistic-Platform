package com.gotruck.truckcategoryservice.dto;

import jakarta.persistence.Column;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TruckNameDTO {
    private Long id;
    @Column(nullable = false) // Enforce uniqueness and not null
    private String name;
}
package com.gotruck.truckcategoryservice.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class TruckNameDTO {
    private Long id;
    @Column(nullable = false) // Enforce uniqueness and not null
    private String name;

}
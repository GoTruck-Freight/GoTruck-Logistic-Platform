package com.gotruck.truckcategoryservice.model;

import jakarta.persistence.*;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "truck_names") // Use snake_case for table names
public class TruckName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) // Enforce uniqueness and not null
    private String name;
}


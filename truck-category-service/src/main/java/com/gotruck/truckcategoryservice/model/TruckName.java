package com.gotruck.truckcategoryservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "truck-names")
public class TruckName {
    @Id
    @GeneratedValue
    @JdbcTypeCode(Types.VARCHAR)
    @Column(columnDefinition = "uuid")
    private UUID id;
    private String name;
}


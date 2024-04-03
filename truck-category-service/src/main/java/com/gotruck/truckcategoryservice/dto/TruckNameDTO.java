package com.gotruck.truckcategoryservice.dto;

import com.gotruck.truckcategoryservice.model.TruckCategory;
import com.gotruck.truckcategoryservice.model.TruckName;
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
    private String name;

}
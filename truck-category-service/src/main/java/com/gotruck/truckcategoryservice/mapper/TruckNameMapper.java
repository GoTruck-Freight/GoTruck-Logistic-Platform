package com.gotruck.truckcategoryservice.mapper;

import com.gotruck.truckcategoryservice.model.dto.TruckNameDTO;
import com.gotruck.truckcategoryservice.dao.entity.TruckName;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TruckNameMapper {
    TruckNameMapper INSTANCE = Mappers.getMapper(TruckNameMapper.class);

    TruckName dtoToTruckName(TruckNameDTO dto);

    TruckNameDTO truckNameToDto(TruckName entity);

    void updateTruckNameFromDTO(TruckNameDTO truckNameDTO, @MappingTarget TruckName truckName);
}

package com.gotruck.truckcategoryservice.mapper;

import com.gotruck.truckcategoryservice.dto.TruckNameDTO;
import com.gotruck.truckcategoryservice.model.TruckName;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TruckNameMapper {
    TruckNameMapper INSTANCE = Mappers.getMapper(TruckNameMapper.class);

//    @Mapping(target = "id", ignore = true)
    TruckName dtoToTruckName(TruckNameDTO dto);

    TruckNameDTO truckNameToDto(TruckName entity);

    void updateTruckNameFromDTO(TruckNameDTO truckNameDTO, @MappingTarget TruckName truckName);
}

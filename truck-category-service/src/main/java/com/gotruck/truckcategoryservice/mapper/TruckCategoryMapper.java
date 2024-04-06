package com.gotruck.truckcategoryservice.mapper;

import com.gotruck.truckcategoryservice.dto.TruckCategoryDTO;
import com.gotruck.truckcategoryservice.model.TruckCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TruckCategoryMapper {
    TruckCategoryMapper INSTANCE = Mappers.getMapper(TruckCategoryMapper.class);

    @Mapping(target = "id", ignore = true)
    TruckCategory dtoToTruckCategory(TruckCategoryDTO dto);

    TruckCategoryDTO truckCategoryToDto(TruckCategory entity);

    void updateTruckCategoryFromDTO(TruckCategoryDTO truckCategoryDTO, @MappingTarget TruckCategory truckCategory);
}


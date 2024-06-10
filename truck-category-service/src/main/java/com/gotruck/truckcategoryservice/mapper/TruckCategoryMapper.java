package com.gotruck.truckcategoryservice.mapper;

import com.gotruck.truckcategoryservice.model.dto.TruckCategoryDTO;
import com.gotruck.truckcategoryservice.dao.entity.TruckCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TruckCategoryMapper {

    TruckCategoryMapper INSTANCE = Mappers.getMapper(TruckCategoryMapper.class);

    TruckCategory dtoToTruckCategory(TruckCategoryDTO dto);

    TruckCategoryDTO truckCategoryToDto(TruckCategory entity);

    void updateTruckCategoryFromDto(TruckCategoryDTO dto, @MappingTarget TruckCategory entity);
}
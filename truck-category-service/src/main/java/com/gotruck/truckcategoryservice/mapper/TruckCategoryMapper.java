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

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "maxLoadCapacity", source = "dto.maxLoadCapacity")
    @Mapping(target = "cargoAreaWidth", source = "dto.cargoAreaWidth")
    @Mapping(target = "cargoAreaLength", source = "dto.cargoAreaLength")
    @Mapping(target = "cargoAreaHeight", source = "dto.cargoAreaHeight")
    @Mapping(target = "cargoCubicVolume", source = "dto.cargoCubicVolume")
    @Mapping(target = "truckNameId", source = "dto.truckNameId")
    TruckCategory dtoToTruckCategory(TruckCategoryDTO dto);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "description", source = "entity.description")
    @Mapping(target = "maxLoadCapacity", source = "entity.maxLoadCapacity")
    @Mapping(target = "cargoAreaWidth", source = "entity.cargoAreaWidth")
    @Mapping(target = "cargoAreaLength", source = "entity.cargoAreaLength")
    @Mapping(target = "cargoAreaHeight", source = "entity.cargoAreaHeight")
    @Mapping(target = "cargoCubicVolume", source = "entity.cargoCubicVolume")
    @Mapping(target = "truckNameId", source = "entity.truckNameId")
    TruckCategoryDTO truckCategoryToDto(TruckCategory entity);

    @Mapping(target = "id", ignore = true) // Ignore the id during the update
    @Mapping(target = "description", source = "dto.description", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "maxLoadCapacity", source = "dto.maxLoadCapacity", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "cargoAreaWidth", source = "dto.cargoAreaWidth", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "cargoAreaLength", source = "dto.cargoAreaLength", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "cargoAreaHeight", source = "dto.cargoAreaHeight", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "cargoCubicVolume", source = "dto.cargoCubicVolume", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "truckNameId", source = "dto.truckNameId", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTruckCategoryFromDto(TruckCategoryDTO dto, @MappingTarget TruckCategory entity);
}
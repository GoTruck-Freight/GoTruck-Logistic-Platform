package com.gotruck.orderservice.mapper;


import com.gotruck.orderservice.dto.OrderDTO;
import com.gotruck.orderservice.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "id", ignore = true)
    Order dtoToOrder(OrderDTO dto);

//    @Mapping(target = "orderType", ignore = true)
    @Mapping(target = "orderType", source = "orderType", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
    OrderDTO orderToDto(Order entity);

}
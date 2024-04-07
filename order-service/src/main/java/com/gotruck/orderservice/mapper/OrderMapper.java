package com.gotruck.orderservice.mapper;


import com.gotruck.orderservice.dto.OrderDTO;
import com.gotruck.orderservice.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.Mapping;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

//    @Mapping(target = "id", ignore = true)
    Order dtoToOrder(OrderDTO dto);

    OrderDTO orderToDto(Order entity);

    void updateOrderFromDTO(OrderDTO orderDTO, @MappingTarget Order order);
}
package com.gotruck.orderservice.mapper;

import com.gotruck.common.dto.order.*;
import com.gotruck.common.model.enums.order.OrderStatus;
import com.gotruck.orderservice.model.Order;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order toEntity(NewOrderDTO dto);

    void updateOrderFromDTO(NewOrderDTO newOrderDTO, @MappingTarget Order order);

    NewOrderDTO toNewOrderDTO(Order order);

    AcceptedOrderDTO toAcceptedOrderDTO(Order order);

    LoadedOrderDTO toLoadedOrderDTO(Order order);

    InTransitOrderDTO toInTransitOrderDTO(Order order);

    UnloadedOrderDTO toUnloadedOrderDTO(Order order);

    CompletedOrderDTO toCompletedOrderDTO(Order order);

    CancelledOrderDTO toCancelledOrderDTO(Order order);

    AllOrderDTO toAllOrderDTO(Order order);

    @AfterMapping
    default void mapOrderToAllOrderDTO(Order order, @MappingTarget AllOrderDTO allOrderDTO) {
        if (order.getOrderStatus() == OrderStatus.ACTIVE) {
            allOrderDTO.setNewOrderDTO(toNewOrderDTO(order));
        }
        if (order.getOrderStatus() == OrderStatus.ACCEPTED) {
            allOrderDTO.setAcceptedOrderDTO(toAcceptedOrderDTO(order));
        }
        if (order.getOrderStatus() == OrderStatus.LOADED) {
            allOrderDTO.setLoadedOrderDTO(toLoadedOrderDTO(order));
        }
        if (order.getOrderStatus() == OrderStatus.IN_TRANSIT) {
            allOrderDTO.setInTransitOrderDTO(toInTransitOrderDTO(order));
        }
        if (order.getOrderStatus() == OrderStatus.UNLOADED) {
            allOrderDTO.setUnloadedOrderDTO(toUnloadedOrderDTO(order));
        }
        if (order.getOrderStatus() == OrderStatus.COMPLETED) {
            allOrderDTO.setCompletedOrderDTO(toCompletedOrderDTO(order));
        }
        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            allOrderDTO.setCancelledOrderDTO(toCancelledOrderDTO(order));
        }
    }
}


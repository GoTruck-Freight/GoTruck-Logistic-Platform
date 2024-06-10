package com.gotruck.orderservice.mapper;

import com.gotruck.common.dto.order.OrderDTO;
import com.gotruck.orderservice.dao.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

//    @Mapping(source = "references.mapId", target = "mapId")
//    @Mapping(source = "references.shipperId", target = "shipperId")
//    @Mapping(source = "references.truckCategoryId", target = "truckCategoryId")
//    @Mapping(source = "references.pricingId", target = "pricingId")
//    @Mapping(source = "references.trackingId", target = "trackingId")
//    @Mapping(source = "references.driverId", target = "driverId")
//    @Mapping(source = "references.brokerId", target = "brokerId")
//    @Mapping(source = "references.paymentId", target = "paymentId")
    OrderDTO toDTO(OrderEntity orderEntity);

//    @Mapping(source = "mapId", target = "references.mapId")
//    @Mapping(source = "shipperId", target = "references.shipperId")
//    @Mapping(source = "truckCategoryId", target = "references.truckCategoryId")
//    @Mapping(source = "pricingId", target = "references.pricingId")
//    @Mapping(source = "trackingId", target = "references.trackingId")
//    @Mapping(source = "driverId", target = "references.driverId")
//    @Mapping(source = "brokerId", target = "references.brokerId")
//    @Mapping(source = "paymentId", target = "references.paymentId")
    OrderEntity toEntity(OrderDTO orderDTO);

//    @Mapping(target = "id", ignore = true) // Ignore the id during the update
//    @Mapping(target = "proposedPayment", source = "dto.proposedPayment", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(target = "truckNameId", source = "dto.truckNameId", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(target = "totalWeight", source = "dto.totalWeight", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(target = "deliveryRoute", source = "dto.deliveryRoute", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(target = "pickupLocation", source = "dto.pickupLocation", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(target = "deliveryLocation", source = "dto.deliveryLocation", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(target = "orderType", source = "dto.orderType", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(target = "pickupDate", source = "dto.pickupDate", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(target = "note", source = "dto.note", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(target = "isActive", source = "dto.isActive", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrderFromDto(OrderDTO dto, @MappingTarget OrderEntity entity);
}
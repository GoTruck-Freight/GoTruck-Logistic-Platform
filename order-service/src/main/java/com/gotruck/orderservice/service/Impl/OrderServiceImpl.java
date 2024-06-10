package com.gotruck.orderservice.service.Impl;

import com.gotruck.common.dto.order.OrderDTO;
import com.gotruck.common.dto.truckCategory.TruckNameDTO;
import com.gotruck.common.model.enums.order.OrderType;
import com.gotruck.orderservice.client.TruckNameClient;
import com.gotruck.orderservice.exceptions.OrderNotFoundException;
import com.gotruck.orderservice.mapper.OrderMapper;
import com.gotruck.orderservice.dao.entity.OrderEntity;
import com.gotruck.orderservice.dao.repository.OrderRepository;
import com.gotruck.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;


import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final TruckNameClient truckNameClient;

    @Override
    public List<OrderDTO> getAllOrders() {
        List<OrderEntity> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO findOrderById(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));
        return orderMapper.toDTO(order);
    }

    @Override
    public List<OrderDTO> findByOrderType(OrderType orderType) {
        List<OrderEntity> orders = orderRepository.findByOrderType(orderType);
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found with order type: " + orderType);
        }
        return orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO createNewOrder(OrderDTO orderDTO) {
        // Fetch the TruckName entity
        TruckNameDTO truckNameDTO = truckNameClient.getTruckNameById(orderDTO.getTruckNameId());
        if (truckNameDTO == null) {
            throw new OrderNotFoundException("TruckName not found with id: " +orderDTO.getTruckNameId());
        }
        OrderEntity orderEntity = orderMapper.toEntity(orderDTO);
        orderEntity = orderRepository.save(orderEntity);
        return orderMapper.toDTO(orderEntity);
    }

    @Override
    public OrderDTO updateOrder(Long orderId, OrderDTO orderDTO) {
        OrderEntity existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));
        orderMapper.updateOrderFromDto(orderDTO, existingOrder);
        existingOrder = orderRepository.save(existingOrder);
        return orderMapper.toDTO(existingOrder);
    }

    @Override
    public OrderDTO patchOrder(Long orderId, Map<String, Object> fields) {
        OrderEntity existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(OrderEntity.class, k);
            if (field != null) {
                field.setAccessible(true);
                Object value = v;
                if (field.getType().equals(Long.class) && v instanceof Integer) {
                    value = Long.valueOf((Integer) v);
                } else if (field.getType().equals(BigDecimal.class) && v instanceof String) {
                    value = new BigDecimal((String) v);
                }
                ReflectionUtils.setField(field, existingOrder, value);
            }
        });

        OrderEntity updatedOrder = orderRepository.save(existingOrder);
        return orderMapper.toDTO(updatedOrder);
    }


    @Override
    public void deleteOrder(Long orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
        } else {
            throw new OrderNotFoundException("Order not found with id: " + orderId);
        }
    }
}
package com.gotruck.orderservice.service.Impl;

import com.gotruck.common.dto.order.*;
import com.gotruck.common.dto.truckCategory.TruckNameDTO;
import com.gotruck.common.model.enums.order.OrderStatus;
import com.gotruck.common.model.enums.order.OrderType;
import com.gotruck.orderservice.client.TruckNameClient;
import com.gotruck.orderservice.exceptions.OrderNotFoundException;
import com.gotruck.orderservice.mapper.OrderMapper;
import com.gotruck.orderservice.model.Order;
import com.gotruck.orderservice.repository.OrderRepository;
import com.gotruck.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final TruckNameClient truckNameClient;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, TruckNameClient truckNameClient) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.truckNameClient = truckNameClient;
    }

    @Override
    public List<AllOrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toAllOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AllOrderDTO findOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));
        AllOrderDTO allOrderDTO = orderMapper.toAllOrderDTO(order);
        orderMapper.mapOrderToAllOrderDTO(order, allOrderDTO);
        return allOrderDTO;
    }

    @Override
    public List<AllOrderDTO> findByOrderType(OrderType orderType) {
        List<Order> orders = orderRepository.findByOrderType(orderType);
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found with order type: " + orderType);
        }
        //  orders.forEach(order -> System.out.println("Found order: " + order)); // Debugging
        return orders.stream()
                .map(order -> {
                    AllOrderDTO allOrderDTO = orderMapper.toAllOrderDTO(order);
                    orderMapper.mapOrderToAllOrderDTO(order, allOrderDTO);
        //             System.out.println("Mapped AllOrderDTO: " + allOrderDTO); // Debugging
                    return allOrderDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<AllOrderDTO> findByOrderStatus(OrderStatus orderStatus) {
        List<Order> orders = orderRepository.findByOrderStatus(orderStatus);
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found with order type: " + orderStatus);
        }
        return orders.stream()
                .map(order -> {
                    AllOrderDTO allOrderDTO = orderMapper.toAllOrderDTO(order);
                    orderMapper.mapOrderToAllOrderDTO(order, allOrderDTO);
                    return allOrderDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public NewOrderDTO createNewOrder(NewOrderDTO newOrderDTO) {
        // Fetch the TruckName entity
        TruckNameDTO truckNameDTO = truckNameClient.getTruckNameById(newOrderDTO.getTruckNameId());
        if (truckNameDTO == null) {
            throw new OrderNotFoundException("TruckName not found with id: " + newOrderDTO.getTruckNameId());
        }
        // Map the DTO to the Order entity
        Order newOrder = orderMapper.toEntity(newOrderDTO);
        newOrder.setOrderStatus(OrderStatus.ACTIVE);
        newOrder.setTruckNameId(truckNameDTO.getId());
        // Save the new Order entity
        Order savedOrder = orderRepository.save(newOrder);
        // Map the saved Order entity back to a DTO
        return orderMapper.toNewOrderDTO(savedOrder);
    }

    @Override
    public NewOrderDTO updateOrder(Long orderId, NewOrderDTO newOrderDTO) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));
        orderMapper.updateOrderFromDTO(newOrderDTO, existingOrder);
        existingOrder = orderRepository.save(existingOrder);
        return orderMapper.toNewOrderDTO(existingOrder);
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
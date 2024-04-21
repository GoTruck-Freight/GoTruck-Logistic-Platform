package com.gotruck.orderservice.service.Impl;

import com.gotruck.commonlibs.dto.TruckNameDTO;
import com.gotruck.orderservice.client.TruckNameClient;
import com.gotruck.orderservice.dto.OrderDTO;
import com.gotruck.orderservice.exceptions.OrderNotFoundException;
import com.gotruck.orderservice.mapper.OrderMapper;
import com.gotruck.orderservice.model.Order;
import com.gotruck.orderservice.model.enums.OrderType;
import com.gotruck.orderservice.repository.OrderRepository;
import com.gotruck.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
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
    public List<OrderDTO> getAllOrders() {
        try{
            List<Order> orders = orderRepository.findAll();
            return orders.stream()
                    .map(orderMapper::orderToDto)
                    .collect(Collectors.toList());}
            catch(Exception e) {
                throw new RuntimeException("Error fetching all orders");
            }
    }

    @Override
    public OrderDTO findOrderById(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()){
            Order order = orderOptional.get();
            return orderMapper.orderToDto(order);
        } else {
            throw new OrderNotFoundException("Order not found with id: " + id);
        }
    }

    @Override
    public List<OrderDTO> findByOrderType(OrderType orderType){
        List<Order> ordersByType = orderRepository.findByOrderType(orderType);
    if (ordersByType.isEmpty()){
        throw new OrderNotFoundException("No orders found with order type: " + orderType);
    }
    return ordersByType.stream()
            .map(orderMapper::orderToDto)
            .collect(Collectors.toList());
}

    @Override
    public OrderDTO addNewOrder(OrderDTO orderDTO) {
        // TruckName entitisini tapmaq
        TruckNameDTO truckNameDTO = truckNameClient.getTruckNameById(orderDTO.getTruckNameId());
        if (truckNameDTO != null) {
            Order newOrder = orderMapper.dtoToOrder(orderDTO);
            newOrder.setTruckNameId(truckNameDTO.getId());
            Order savedOrder = orderRepository.save(newOrder);
            return orderMapper.orderToDto(savedOrder);
        } else {
            throw new OrderNotFoundException("TruckName not found with id: " + orderDTO.getTruckNameId());
        }
    }

    @Override
    public OrderDTO updateOrder(Long id, OrderDTO updatedOrderDTO) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();

            if (updatedOrderDTO.getMinPayment() != null) {
                order.setMaxPayment(updatedOrderDTO.getMaxPayment());
            }
            if (updatedOrderDTO.getMaxPayment() != null){
            order.setMinPayment(updatedOrderDTO.getMinPayment());
            }
            if (updatedOrderDTO.getProposedPayment() != null){
            order.setProposedPayment(updatedOrderDTO.getProposedPayment());
            }
            if (updatedOrderDTO.getTotalWeight() != null) {
                order.setTotalWeight(updatedOrderDTO.getTotalWeight());
            }
            if (updatedOrderDTO.getDeliveryLocation() != null) {
                order.setDeliveryRoute(updatedOrderDTO.getDeliveryRoute());
            }
            if (updatedOrderDTO.getPickupDate() != null) {
                order.setPickupLocation(updatedOrderDTO.getPickupLocation());
            }
            if (updatedOrderDTO.getDeliveryLocation() != null) {
                order.setDeliveryLocation(updatedOrderDTO.getDeliveryLocation());
            }
            if (updatedOrderDTO.getOrderType() != null) {
                order.setOrderType(updatedOrderDTO.getOrderType());
            }
            if (updatedOrderDTO.getPickupDate() != null) {
                order.setPickupDate(updatedOrderDTO.getPickupDate());
            }
            if (updatedOrderDTO.getNote() != null) {
                order.setNote(updatedOrderDTO.getNote());
            }

            Order updatedOrder = orderRepository.save(order);
            return orderMapper.orderToDto(updatedOrder);
        } else {
            throw new OrderNotFoundException("Order not found with id: " + id);
        }
    }

    @Override
    public void deleteOrder(Long id) {
        if(orderRepository.existsById(id)){
            orderRepository.deleteById(id);
        } else {
            throw new OrderNotFoundException("Order not found with id: " + id);
        }
    }
}

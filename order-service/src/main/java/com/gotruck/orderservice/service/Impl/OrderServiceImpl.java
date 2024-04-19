package com.gotruck.orderservice.service.Impl;

import com.gotruck.commonlibs.dto.TruckNameDTO;
import com.gotruck.orderservice.client.TruckNameClient;
import com.gotruck.orderservice.dto.OrderDTO;
import com.gotruck.orderservice.exceptions.OrderNotFoundException;
import com.gotruck.orderservice.mapper.OrderMapper;
import com.gotruck.orderservice.model.Order;
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

            order.setMaxPayment(updatedOrderDTO.getMaxPayment());
            order.setMinPayment(updatedOrderDTO.getMinPayment());
            order.setProposedPayment(updatedOrderDTO.getProposedPayment());
            order.setTotalWeight(updatedOrderDTO.getTotalWeight());
            order.setDeliveryRoute(updatedOrderDTO.getDeliveryRoute());
            order.setPickupLocation(updatedOrderDTO.getPickupLocation());
            order.setDeliveryLocation(updatedOrderDTO.getDeliveryLocation());
            order.setOrderType(updatedOrderDTO.getOrderType());
            order.setPickupDate(updatedOrderDTO.getPickupDate());
            order.setNote(updatedOrderDTO.getNote());

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

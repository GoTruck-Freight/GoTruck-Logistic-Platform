package com.gotruck.orderservice.service.Impl;

import com.gotruck.orderservice.dto.OrderDTO;
import com.gotruck.orderservice.exceptions.OrderNotFoundException;
import com.gotruck.orderservice.mapper.OrderMapper;
import com.gotruck.orderservice.model.Order;
import com.gotruck.orderservice.repository.OrderRepository;
import com.gotruck.orderservice.service.OrderService;
import com.gotruck.truckcategoryservice.model.TruckName;
import com.gotruck.truckcategoryservice.repository.TruckNameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final TruckNameRepository truckNameRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, TruckNameRepository truckNameRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.truckNameRepository = truckNameRepository;
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
//        Optional<TruckName> truckNameOptional = orderRepository.findById(orderDTO.getTruckNameId());
//        if (truckNameOptional.isPresent()) {
//            Order newOrder = orderMapper.dtoToOrder(orderDTO);
//            newOrder.setTruckNameId(orderDTO.getTruckNameId());
//
//            Order savedOrder = orderRepository.save(newOrder);
//            return orderMapper.orderToDto(savedOrder);
//        } else {
//            throw new OrderNotFoundException("Order not found with id: " + orderDTO.getTruckNameId());
//        }
        Optional<TruckName> truckNameOptional = truckNameRepository.findById(orderDTO.getTruckNameId());
        if (truckNameOptional.isPresent()) {
            Order newOrder = orderMapper.dtoToOrder(orderDTO);
            newOrder.setTruckNameId(truckNameOptional.get().getId());
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

            if (Objects.nonNull(updatedOrderDTO.getMaxPayment())) {
                order.setMaxPayment(updatedOrderDTO.getMaxPayment());
            }
            if (Objects.nonNull(updatedOrderDTO.getMinPayment())) {
                order.setMinPayment(updatedOrderDTO.getMinPayment());
            }
            if (Objects.nonNull(updatedOrderDTO.getProposedPayment())) {
                order.setProposedPayment(updatedOrderDTO.getProposedPayment());
            }
            if (Objects.nonNull(updatedOrderDTO.getTotalWeight())) {
                order.setTotalWeight(updatedOrderDTO.getTotalWeight());
            }
            if (Objects.nonNull(updatedOrderDTO.getDeliveryRoute())) {
                order.setDeliveryRoute(updatedOrderDTO.getDeliveryRoute());
            }
            if (Objects.nonNull(updatedOrderDTO.getPickupLocation())) {
                order.setPickupLocation(updatedOrderDTO.getPickupLocation());
            }
            if (Objects.nonNull(updatedOrderDTO.getDeliveryLocation())) {
                order.setDeliveryLocation(updatedOrderDTO.getDeliveryLocation());
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

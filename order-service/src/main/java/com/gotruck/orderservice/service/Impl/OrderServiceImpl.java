package com.gotruck.orderservice.service.Impl;

import com.gotruck.orderservice.dto.OrderDTO;
import com.gotruck.orderservice.exceptions.OrderNotFoundException;
import com.gotruck.orderservice.mapper.OrderMapper;
import com.gotruck.orderservice.model.Order;
import com.gotruck.orderservice.repository.OrderRepository;
import com.gotruck.orderservice.service.OrderService;
//import com.gotruck.truckcategoryservice.model.TruckName;
//import com.gotruck.truckcategoryservice.repository.TruckNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
//    private final TruckNameRepository truckNameRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
//        this.truckNameRepository = truckNameRepository;
        this.restTemplate = restTemplate;
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
        // TruckName məlumatını REST API ilə çəkirik
        String truckCategoryServiceUrl = "http://localhost:9080/truck-category/api/truck-names/";
        ResponseEntity<TruckNameDTO> response = restTemplate.getForEntity(truckCategoryServiceUrl + orderDTO.getTruckNameId(), TruckNameDTO.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            TruckNameDTO truckNameDTO = response.getBody();
            Order newOrder = orderMapper.dtoToOrder(orderDTO);
            newOrder.setTruckNameId(truckNameDTO.getId()); // TruckName id-ni set edirik
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

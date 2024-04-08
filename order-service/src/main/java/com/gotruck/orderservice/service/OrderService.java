package com.gotruck.orderservice.service;

import com.gotruck.orderservice.dto.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    List<OrderDTO> getAllOrders();
    OrderDTO findOrderById(Long id);
    OrderDTO addNewOrder(OrderDTO orderDTO);
    OrderDTO updateOrder(Long id, OrderDTO orderDTO);
    void deleteOrder(Long id);

}
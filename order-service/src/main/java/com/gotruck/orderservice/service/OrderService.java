package com.gotruck.orderservice.service;

import com.gotruck.common.dto.order.*;
import com.gotruck.common.model.enums.order.OrderStatus;
import com.gotruck.common.model.enums.order.OrderType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    List<AllOrderDTO> getAllOrders();
    AllOrderDTO findOrderById(Long orderId);
    List<AllOrderDTO> findByOrderType(OrderType orderType);
    List<AllOrderDTO> findByOrderStatus(OrderStatus orderStatus);
    NewOrderDTO createNewOrder(NewOrderDTO newOrderDTO);
    NewOrderDTO updateOrder(Long orderId, NewOrderDTO newOrderDTO);
    void deleteOrder(Long orderId);
}

package com.gotruck.orderservice.service;

import com.gotruck.common.dto.order.*;
import com.gotruck.common.model.enums.order.OrderStatus;
import com.gotruck.common.model.enums.order.OrderType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface OrderService {
    List<OrderDTO> getAllOrders();
    OrderDTO findOrderById(Long orderId);
    List<OrderDTO> findByOrderType(OrderType orderType);
    OrderDTO createNewOrder(OrderDTO OrderDTO);
    OrderDTO updateOrder(Long orderId, OrderDTO OrderDTO);

    OrderDTO patchOrder(Long orderId, Map<String, Object> fields);

    void deleteOrder(Long orderId);
}

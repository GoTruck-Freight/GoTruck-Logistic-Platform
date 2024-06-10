package com.gotruck.shipperservice.service;

import com.gotruck.common.dto.order.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShipperOrderService {

    List<OrderDTO> getAllOrders(String token);

    OrderDTO findOrderById(Long id, String token);

    List<OrderDTO> findByOrderType(String orderType, String token);

    List<OrderDTO> findByOrderStatus(String orderStatus, String token);

    OrderDTO createOrder(OrderDTO newOrderDTO, String token);

    OrderDTO updateOrder(Long id, OrderDTO newOrderDTO, String token);

    OrderDTO deleteOrder(Long id, String token);
}


package com.gotruck.shipperservice.service;

import com.gotruck.common.dto.order.AllOrderDTO;
import com.gotruck.common.dto.order.NewOrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShipperOrderService {

    List<AllOrderDTO> getAllOrders(String token);

    AllOrderDTO findOrderById(Long id, String token);

    List<AllOrderDTO> findByOrderType(String orderType, String token);

    List<AllOrderDTO> findByOrderStatus(String orderStatus, String token);

    NewOrderDTO createOrder(NewOrderDTO newOrderDTO, String token);

    NewOrderDTO updateOrder(Long id, NewOrderDTO newOrderDTO, String token);

    AllOrderDTO deleteOrder(Long id, String token);
}


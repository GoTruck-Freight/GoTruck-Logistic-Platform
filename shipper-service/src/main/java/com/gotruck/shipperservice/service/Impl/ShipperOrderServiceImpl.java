package com.gotruck.shipperservice.service.Impl;

import com.gotruck.common.dto.order.*;
import com.gotruck.shipperservice.client.OrderClient;
import com.gotruck.shipperservice.exceptions.UnauthorizedException;
import com.gotruck.shipperservice.service.JwtService;
import com.gotruck.shipperservice.service.ShipperOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipperOrderServiceImpl implements ShipperOrderService {

    private final OrderClient orderClient;
    private final JwtService jwtService;

    @Override
    public List<OrderDTO> getAllOrders(String token) {
        return orderClient.getAllOrders("Bearer " + token);
    }

    @Override
    public OrderDTO findOrderById(Long id, String token) {
        return orderClient.findOrderById(id, "Bearer " + token);
    }

    @Override
    public List<OrderDTO> findByOrderType(String orderType, String token) {
        return orderClient.findByOrderType(orderType, "Bearer " + token);
    }

    @Override
    public List<OrderDTO> findByOrderStatus(String orderStatus, String token) {
        return orderClient.findByOrderStatus(orderStatus, "Bearer " + token);
    }

    @Override
    public OrderDTO createOrder(OrderDTO newOrderDTO, String token) {
        Long shipperId = jwtService.extractUserId(token);
        if (newOrderDTO.getReferences().getShipperId() == null || newOrderDTO.getReferences().getShipperId().equals(shipperId)) {
            newOrderDTO.getReferences().setShipperId(shipperId);
            return orderClient.createOrder(newOrderDTO, token);
        } else {
            throw new UnauthorizedException("You can only create orders for your own account");
        }
    }

    @Override
    public OrderDTO updateOrder(Long id, OrderDTO newOrderDTO, String token) {
        Long shipperId = jwtService.extractUserId(token);
        newOrderDTO.getReferences().setShipperId(shipperId);
        return orderClient.updateOrder(id, newOrderDTO, token);
    }

    @Override
    public OrderDTO deleteOrder(Long id, String token) {
        return orderClient.findOrderById(id, token);

    }
}

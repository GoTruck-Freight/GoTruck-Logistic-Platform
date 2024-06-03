package com.gotruck.shipperservice.service.Impl;

import com.gotruck.common.dto.order.*;
import com.gotruck.shipperservice.client.OrderClient;
import com.gotruck.shipperservice.exceptions.UnauthorizedException;
import com.gotruck.shipperservice.service.JwtService;
import com.gotruck.shipperservice.service.ShipperOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipperOrderServiceImpl implements ShipperOrderService {

    private final OrderClient orderClient;
    private final JwtService jwtService;

    @Override
    public List<AllOrderDTO> getAllOrders(String token) {
        return orderClient.getAllOrders("Bearer " + token);
    }

    @Override
    public AllOrderDTO findOrderById(Long id, String token) {
        return orderClient.findOrderById(id, "Bearer " + token);
    }

    @Override
    public List<AllOrderDTO> findByOrderType(String orderType, String token) {
        return orderClient.findByOrderType(orderType, "Bearer " + token);
    }

    @Override
    public List<AllOrderDTO> findByOrderStatus(String orderStatus, String token) {
        return orderClient.findByOrderStatus(orderStatus, "Bearer " + token);
    }

    @Override
    public NewOrderDTO createOrder(NewOrderDTO newOrderDTO, String token) {
        Long shipperId = jwtService.extractUserId(token);
        if (newOrderDTO.getShipperId() == null || newOrderDTO.getShipperId().equals(shipperId)) {
            newOrderDTO.setShipperId(shipperId);
            return orderClient.createOrder(newOrderDTO, token);
        } else {
            throw new UnauthorizedException("You can only create orders for your own account");
        }
    }

    @Override
    public NewOrderDTO updateOrder(Long id, NewOrderDTO newOrderDTO, String token) {
        Long shipperId = jwtService.extractUserId(token);
        newOrderDTO.setShipperId(shipperId);
        return orderClient.updateOrder(id, newOrderDTO, token);
    }

    @Override
    public AllOrderDTO deleteOrder(Long id, String token) {
        return orderClient.findOrderById(id, token);

    }
}

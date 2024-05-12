package com.gotruck.shipperservice.service;

import com.gotruck.common.dto.OrderDTO;
import com.gotruck.shipperservice.client.OrderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShipperService {

    private final OrderClient orderClient;

    @Autowired
    public ShipperService(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    public OrderDTO getOrder(Long id) {
        return orderClient.findOrderById(id);
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {
        return orderClient.createOrder(orderDTO);
    }

    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        return orderClient.updateOrder(id, orderDTO);
    }

    public void deleteOrder(Long id) {
        orderClient.deleteOrder(id);
    }
}


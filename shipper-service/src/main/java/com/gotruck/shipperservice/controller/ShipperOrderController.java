package com.gotruck.shipperservice.controller;

import com.gotruck.common.dto.order.OrderDTO;
import com.gotruck.shipperservice.service.ShipperOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;


@RestController
@RequestMapping("api/v1/shipper/orders")

public class ShipperOrderController {
    private final ShipperOrderService shipperOrderService;

    @Autowired
    public ShipperOrderController(ShipperOrderService shipperOrderService) {
        this.shipperOrderService = shipperOrderService;
    }

    @GetMapping()
    public List<OrderDTO> geAllOrders(@RequestHeader("Authorization") String token) {
        return shipperOrderService.getAllOrders(token);
    }

    @GetMapping("/{id}")
    public OrderDTO findOrderById(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        return shipperOrderService.findOrderById(id, token);
    }

    @GetMapping("/type/{orderType}")
    public List<OrderDTO> findByOrderType(@PathVariable String orderType, @RequestHeader("Authorization") String token) {
        return shipperOrderService.findByOrderType(orderType, token);
    }

    @GetMapping("/status/{orderStatus}")
    public List<OrderDTO> findByOrderStatus(@PathVariable String orderStatus, @RequestHeader("Authorization") String token) {
        return shipperOrderService.findByOrderStatus(orderStatus, token);
    }

    @PostMapping()
    @ResponseStatus(CREATED)
    public OrderDTO createOrder(@RequestBody OrderDTO orderRequestDTO, @RequestHeader("Authorization") String token) {
        return shipperOrderService.createOrder(orderRequestDTO, token);
    }

    @PutMapping("/{id}")
    public OrderDTO updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO,@RequestHeader("Authorization") String token) {
        return shipperOrderService.updateOrder(id, orderDTO, token);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteOrder(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        shipperOrderService.deleteOrder(id, token);
    }
}

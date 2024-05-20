package com.gotruck.shipperservice.controller;

import com.gotruck.common.dto.order.AllOrderDTO;
import com.gotruck.common.dto.order.NewOrderDTO;
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
    public List<AllOrderDTO> geAllOrders(@RequestHeader("Authorization") String token) {
        return shipperOrderService.getAllOrders(token);
    }

    @GetMapping("/{id}")
    public AllOrderDTO findOrderById(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        return shipperOrderService.findOrderById(id, token);
    }

    @GetMapping("/type/{orderType}")
    public List<AllOrderDTO> findByOrderType(@PathVariable String orderType, @RequestHeader("Authorization") String token) {
        return shipperOrderService.findByOrderType(orderType, token);
    }

    @GetMapping("/status/{orderStatus}")
    public List<AllOrderDTO> findByOrderStatus(@PathVariable String orderStatus, @RequestHeader("Authorization") String token) {
        return shipperOrderService.findByOrderStatus(orderStatus, token);
    }

    @PostMapping()
    @ResponseStatus(CREATED)
    public NewOrderDTO createOrder(@RequestBody NewOrderDTO newOrderRequestDTO, @RequestHeader("Authorization") String token) {
        return shipperOrderService.createOrder(newOrderRequestDTO, token);
    }

    @PutMapping("/{id}")
    public NewOrderDTO updateOrder(@PathVariable Long id, @RequestBody NewOrderDTO newOrderDTO,@RequestHeader("Authorization") String token) {
        return shipperOrderService.updateOrder(id, newOrderDTO, token);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteOrder(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        shipperOrderService.deleteOrder(id, token);
    }
}

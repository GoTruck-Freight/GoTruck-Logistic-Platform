package com.gotruck.orderservice.controller;

import com.gotruck.orderservice.dto.OrderDTO;
import com.gotruck.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    private List<OrderDTO> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderDTO findOrderById(@PathVariable Long id){
        return orderService.findOrderById(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public OrderDTO addNewOrder(@RequestBody OrderDTO orderDTO){
        return orderService.addNewOrder(orderDTO);
    }

    @PutMapping("/{id}")
    public OrderDTO updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO){
        return orderService.updateOrder(id, orderDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
    }
}


package com.gotruck.orderservice.controller;

import com.gotruck.common.dto.order.OrderDTO;
import com.gotruck.common.model.enums.order.OrderType;
import com.gotruck.orderservice.exceptions.ServiceUnavailableException;
import com.gotruck.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/{orderId}")
    @CircuitBreaker(name = "getOrderById", fallbackMethod = "fallback")
    public OrderDTO findOrderById(@PathVariable Long orderId){
        return orderService.findOrderById(orderId);
    }

    public OrderDTO fallback(Long response, Exception exception) {
        throw new ServiceUnavailableException("Service is currently unavailable.");
    }

    @GetMapping("/type/{orderType}")
    public List<OrderDTO> findByOrderType(@PathVariable String orderType){ return orderService.findByOrderType(OrderType.valueOf(orderType));}

    @PostMapping()
    @ResponseStatus(CREATED)
    public OrderDTO createNewOrder(@RequestBody OrderDTO newOrderDTO){
        OrderDTO createdOrder = orderService.createNewOrder(newOrderDTO);
//        System.out.println("Created Order in OrderService: " + createdOrder); // Debugging
        return createdOrder;
    }

    @PutMapping("/{orderId}")
    public OrderDTO updateOrder(@PathVariable Long orderId, @RequestBody OrderDTO orderDTO){
        return orderService.updateOrder(orderId, orderDTO);
    }

    @PatchMapping("/{orderId}")
    public OrderDTO patchOrder(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        return orderService.patchOrder(id, fields);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
    }
}


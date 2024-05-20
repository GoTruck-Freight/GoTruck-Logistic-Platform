package com.gotruck.orderservice.controller;

import com.gotruck.common.dto.order.AllOrderDTO;
import com.gotruck.common.dto.order.NewOrderDTO;
import com.gotruck.common.model.enums.order.OrderStatus;
import com.gotruck.common.model.enums.order.OrderType;
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
    private List<AllOrderDTO> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public AllOrderDTO findOrderById(@PathVariable Long orderId){
        return orderService.findOrderById(orderId);
    }

    @GetMapping("/type/{orderType}")
    public List<AllOrderDTO> findByOrderType(@PathVariable String orderType){ return orderService.findByOrderType(OrderType.valueOf(orderType));}

    @GetMapping("/status/{orderStatus}")
    public List<AllOrderDTO> findByOrderStatus(@PathVariable String orderStatus){ return orderService.findByOrderStatus(OrderStatus.valueOf(orderStatus));}

    @PostMapping()
    @ResponseStatus(CREATED)
    public NewOrderDTO createNewOrder(@RequestBody NewOrderDTO newOrderDTO){
        NewOrderDTO createdOrder = orderService.createNewOrder(newOrderDTO);
//        System.out.println("Created Order in OrderService: " + createdOrder); // Debugging
        return createdOrder;
    }

    @PutMapping("/{orderId}")
    public NewOrderDTO updateOrder(@PathVariable Long orderId, @RequestBody NewOrderDTO newOrderDTO){
        return orderService.updateOrder(orderId, newOrderDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
    }
}


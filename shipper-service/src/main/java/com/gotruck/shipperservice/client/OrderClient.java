package com.gotruck.shipperservice.client;

import com.gotruck.common.dto.order.OrderDTO;
import com.gotruck.shipperservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "order-service", configuration = FeignConfig.class, url = "http://localhost:4044/api/v1/orders")
public interface OrderClient {
    @GetMapping
    List<OrderDTO> getAllOrders(@RequestHeader("Authorization") String token);

    @GetMapping("/{orderId}")
    OrderDTO findOrderById(@PathVariable Long orderId, @RequestHeader("Authorization") String token);

    @GetMapping("/type/{orderType}")
    List<OrderDTO> findByOrderType(@PathVariable String orderType, @RequestHeader("Authorization") String token);

    @GetMapping("/status/{orderStatus}")
    List<OrderDTO> findByOrderStatus(@PathVariable String orderStatus, @RequestHeader("Authorization") String token);

    @PostMapping()
    OrderDTO createOrder(@RequestBody OrderDTO newOrderDTO, @RequestHeader("Authorization") String token);

    @PutMapping("/{id}")
    OrderDTO updateOrder(@PathVariable Long id, @RequestBody OrderDTO newOrderDTO,@RequestHeader("Authorization") String token);

    @DeleteMapping("/{id}")
    void deleteOrder(@PathVariable Long id, @RequestHeader("Authorization") String token);

}

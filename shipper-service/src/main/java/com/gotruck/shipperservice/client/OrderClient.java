package com.gotruck.shipperservice.client;

import com.gotruck.common.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "order-service")
public interface OrderClient {
    @GetMapping("/api/v1/orders/{id}")
    OrderDTO findOrderById(@PathVariable Long id);

    @PostMapping("/api/v1/orders")
    OrderDTO createOrder(@RequestBody OrderDTO orderDTO);

    @PutMapping("/api/v1/orders/{id}")
    OrderDTO updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO);

    @DeleteMapping("/api/v1/orders/{id}")
    void deleteOrder(@PathVariable Long id);
}

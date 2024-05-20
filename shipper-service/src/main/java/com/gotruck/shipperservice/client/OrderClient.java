package com.gotruck.shipperservice.client;

import com.gotruck.common.dto.order.AllOrderDTO;
import com.gotruck.common.dto.order.NewOrderDTO;
import com.gotruck.shipperservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "order-service", configuration = FeignConfig.class, url = "http://localhost:4044/api/v1/orders")
public interface OrderClient {
    @GetMapping
    List<AllOrderDTO> getAllOrders(@RequestHeader("Authorization") String token);

    @GetMapping("/{orderId}")
    AllOrderDTO findOrderById(@PathVariable Long orderId, @RequestHeader("Authorization") String token);

    @GetMapping("/type/{orderType}")
    List<AllOrderDTO> findByOrderType(@PathVariable String orderType, @RequestHeader("Authorization") String token);

    @GetMapping("/status/{orderStatus}")
    List<AllOrderDTO> findByOrderStatus(@PathVariable String orderStatus, @RequestHeader("Authorization") String token);

    @PostMapping()
    NewOrderDTO createOrder(@RequestBody NewOrderDTO newOrderDTO, @RequestHeader("Authorization") String token);

    @PutMapping("/{id}")
    NewOrderDTO updateOrder(@PathVariable Long id, @RequestBody NewOrderDTO newOrderDTO,@RequestHeader("Authorization") String token);

    @DeleteMapping("/{id}")
    void deleteOrder(@PathVariable Long id, @RequestHeader("Authorization") String token);

}

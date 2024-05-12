package com.gotruck.orderservice.client;

import com.gotruck.common.dto.TruckNameDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "truck-category-service", url = "http://localhost:9080")
public interface TruckNameClient {
    @GetMapping("/api/v1/truck-names/{id}")
    TruckNameDTO getTruckNameById(@PathVariable("id") Long id);
}

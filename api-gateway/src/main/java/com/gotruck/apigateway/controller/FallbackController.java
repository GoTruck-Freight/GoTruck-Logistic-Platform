package com.gotruck.apigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("shipperServiceFallback")
    public String shipperServiceFallback() {
        return "Gateway fallback -> Shipper service seems to have a problem processing your request. Please try again later.";
    }

    @GetMapping("truckCategoryServiceFallback")
    public String truckCategoryServiceFallback() {
        return "Gateway fallback -> Truck Category service seems to have a problem processing your request. Please try again later.";
    }

    @GetMapping("orderServiceFallback")
    public String OrderFallback() {
        return "Gateway fallback -> Order service seems to have a problem processing your request. Please try again later.";
    }
}

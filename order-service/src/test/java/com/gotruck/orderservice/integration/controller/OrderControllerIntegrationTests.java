//package com.gotruck.orderservice.integration.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.gotruck.orderservice.controller.OrderController;
//import com.gotruck.orderservice.dto.OrderDTO;
//import com.gotruck.orderservice.model.enums.OrderType;
//import com.gotruck.orderservice.service.OrderService;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Collections;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(OrderController.class)
//public class OrderControllerIntegrationTests {
//
//    @Mock
//    private OrderService orderService;
//
//    @InjectMocks
//    private OrderController orderController;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    public void testGetAllOrders() throws Exception {
//        OrderDTO orderDTO = new OrderDTO();
//        orderDTO.setId(1L);
//        orderDTO.setOrderType(OrderType.SINGLE_TRIP);
//
//        when(orderService.getAllOrders()).thenReturn(Collections.singletonList(orderDTO));
//
//        mockMvc.perform(get("/api/v1/orders"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].id").value(1))
//                .andExpect(jsonPath("$[0].orderType").value("SINGLE_TRIP"));
//    }
//
//    @Test
//    public void testFindOrderById() throws Exception {
//        OrderDTO orderDTO = new OrderDTO();
//        orderDTO.setId(1L);
//        orderDTO.setOrderType(OrderType.SINGLE_TRIP);
//
//        when(orderService.findOrderById(any(Long.class))).thenReturn(orderDTO);
//
//        mockMvc.perform(get("/api/v1/orders/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.orderType").value("SINGLE_TRIP"));
//    }
//
//    @Test
//    public void testAddNewOrder() throws Exception {
//        OrderDTO orderDTO = new OrderDTO();
//        orderDTO.setId(1L);
//        orderDTO.setOrderType(OrderType.SINGLE_TRIP);
//
//        when(orderService.addNewOrder(any(OrderDTO.class))).thenReturn(orderDTO);
//
//        mockMvc.perform(post("/api/v1/orders")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(orderDTO)))
//                .andExpect(status().isCreated())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.orderType").value("SINGLE_TRIP"));
//    }
//
//    @Test
//    public void testUpdateOrder() throws Exception {
//        OrderDTO orderDTO = new OrderDTO();
//        orderDTO.setId(1L);
//        orderDTO.setOrderType(OrderType.SINGLE_TRIP);
//
//        when(orderService.updateOrder(any(Long.class), any(OrderDTO.class))).thenReturn(orderDTO);
//
//        mockMvc.perform(put("/api/v1/orders/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(orderDTO)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.orderType").value("SINGLE_TRIP"));
//    }
//
////    @Test
////    public void testDeleteOrder() throws Exception {
////        mockMvc.perform(delete("/api/v1/orders/1"))
////                .contentType(MediaType.APPLICATION_JSON))
////                .andExpect(status().isNoContent());
////    }
//}

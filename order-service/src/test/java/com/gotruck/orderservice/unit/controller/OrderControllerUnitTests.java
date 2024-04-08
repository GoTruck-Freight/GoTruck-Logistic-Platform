package com.gotruck.orderservice.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gotruck.orderservice.controller.OrderController;
import com.gotruck.orderservice.dto.OrderDTO;
import com.gotruck.orderservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OrderControllerUnitTests {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void testGetAllOrders() throws Exception {
        when(orderService.getAllOrders()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());

        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    public void testFindOrderById() throws Exception {
        Long orderId = 1L;
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(orderId);

        when(orderService.findOrderById(orderId)).thenReturn(orderDTO);

        mockMvc.perform(get("/api/v1/orders/{id}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId));

        verify(orderService, times(1)).findOrderById(orderId);
    }

    @Test
    public void testAddNewOrder() throws Exception {
        OrderDTO orderDTO = new OrderDTO();

        when(orderService.addNewOrder(any(OrderDTO.class))).thenReturn(orderDTO);

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());

        verify(orderService, times(1)).addNewOrder(any(OrderDTO.class));
    }

    @Test
    public void testUpdateOrder() throws Exception {
        Long orderId = 1L;
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(orderId);

        when(orderService.updateOrder(eq(orderId), any(OrderDTO.class))).thenReturn(orderDTO);

        mockMvc.perform(put("/api/v1/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId));

        verify(orderService, times(1)).updateOrder(eq(orderId), any(OrderDTO.class));
    }

    @Test
    public void testDeleteOrder() throws Exception {
        Long orderId = 1L;

        mockMvc.perform(delete("/api/v1/orders/{id}", orderId))
                .andExpect(status().isNoContent());

        verify(orderService, times(1)).deleteOrder(orderId);
    }
}

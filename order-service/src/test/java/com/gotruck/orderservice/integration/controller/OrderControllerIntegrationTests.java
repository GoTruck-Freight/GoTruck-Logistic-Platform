package com.gotruck.orderservice.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gotruck.orderservice.dto.OrderDTO;
import com.gotruck.orderservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        // Burada gerekiyorsa test verileri hazırlanabilir.
    }

    @Test
    public void testAddNewOrder() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setDeliveryRoute("Test Route");
        orderDTO.setPickupLocation("Test Pickup");
        orderDTO.setDeliveryLocation("Test Delivery");

        String jsonRequest = objectMapper.writeValueAsString(orderDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        OrderDTO responseOrder = objectMapper.readValue(jsonResponse, OrderDTO.class);

        // Burada beklenen sonuçları doğrulayabilirsiniz.
        assertEquals(orderDTO.getDeliveryRoute(), responseOrder.getDeliveryRoute());
        assertEquals(orderDTO.getPickupLocation(), responseOrder.getPickupLocation());
        assertEquals(orderDTO.getDeliveryLocation(), responseOrder.getDeliveryLocation());
    }

    @Test
    public void testFindOrderById() throws Exception {
        // Önce bir sipariş ekleyelim
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setDeliveryRoute("Test Route");
        orderDTO.setPickupLocation("Test Pickup");
        orderDTO.setDeliveryLocation("Test Delivery");

        String jsonRequest = objectMapper.writeValueAsString(orderDTO);

        MvcResult addResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = addResult.getResponse().getContentAsString();
        OrderDTO addedOrder = objectMapper.readValue(jsonResponse, OrderDTO.class);

        // Ardından bu siparişi bulma testi yapalım
        MvcResult findResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders/{id}", addedOrder.getId()))
                .andExpect(status().isOk())
                .andReturn();

        String findResponse = findResult.getResponse().getContentAsString();
        OrderDTO foundOrder = objectMapper.readValue(findResponse, OrderDTO.class);

        // Beklenen sonuçları doğrulayalım
        assertEquals(addedOrder.getId(), foundOrder.getId());
        assertEquals(addedOrder.getDeliveryRoute(), foundOrder.getDeliveryRoute());
        assertEquals(addedOrder.getPickupLocation(), foundOrder.getPickupLocation());
        assertEquals(addedOrder.getDeliveryLocation(), foundOrder.getDeliveryLocation());
    }

    @Test
    public void testUpdateOrder() throws Exception {
        // Önce bir sipariş ekleyelim
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setDeliveryRoute("Test Route");
        orderDTO.setPickupLocation("Test Pickup");
        orderDTO.setDeliveryLocation("Test Delivery");

        String jsonRequest = objectMapper.writeValueAsString(orderDTO);

        MvcResult addResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = addResult.getResponse().getContentAsString();
        OrderDTO addedOrder = objectMapper.readValue(jsonResponse, OrderDTO.class);

        // Siparişi güncelleme testi yapalım
        orderDTO.setDeliveryRoute("Updated Route");
        orderDTO.setPickupLocation("Updated Pickup");
        orderDTO.setDeliveryLocation("Updated Delivery");

        String updateRequest = objectMapper.writeValueAsString(orderDTO);

        MvcResult updateResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/orders/{id}", addedOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequest))
                .andExpect(status().isOk())
                .andReturn();

        String updateResponse = updateResult.getResponse().getContentAsString();
        OrderDTO updatedOrder = objectMapper.readValue(updateResponse, OrderDTO.class);

        // Beklenen sonuçları doğrulayalım
        assertEquals(addedOrder.getId(), updatedOrder.getId());
        assertEquals(orderDTO.getDeliveryRoute(), updatedOrder.getDeliveryRoute());
        assertEquals(orderDTO.getPickupLocation(), updatedOrder.getPickupLocation());
        assertEquals(orderDTO.getDeliveryLocation(), updatedOrder.getDeliveryLocation());
    }

    @Test
    public void testDeleteOrder() throws Exception {
        // Önce bir sipariş ekleyelim
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setDeliveryRoute("Test Route");
        orderDTO.setPickupLocation("Test Pickup");
        orderDTO.setDeliveryLocation("Test Delivery");

        String jsonRequest = objectMapper.writeValueAsString(orderDTO);

        MvcResult addResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = addResult.getResponse().getContentAsString();
        OrderDTO addedOrder = objectMapper.readValue(jsonResponse, OrderDTO.class);

        // Siparişi silme testi yapalım
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/orders/{id}", addedOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Siparişi sildikten sonra tekrar bulma isteği gönderelim
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders/{id}", addedOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}

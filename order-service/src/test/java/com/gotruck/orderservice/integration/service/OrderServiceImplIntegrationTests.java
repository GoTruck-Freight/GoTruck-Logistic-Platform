package com.gotruck.orderservice.integration.service;

import com.gotruck.orderservice.dto.OrderDTO;
import com.gotruck.orderservice.exceptions.OrderNotFoundException;
import com.gotruck.orderservice.mapper.OrderMapper;
import com.gotruck.orderservice.model.Order;
import com.gotruck.orderservice.repository.OrderRepository;
import com.gotruck.orderservice.service.Impl.OrderServiceImpl;
import com.gotruck.truckcategoryservice.model.TruckName;
import com.gotruck.truckcategoryservice.repository.TruckNameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServiceImplIntegrationTests {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private TruckNameRepository truckNameRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllOrders() {
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());

        assertTrue(orderService.getAllOrders().isEmpty());
    }

    @Test
    public void testFindOrderById() {
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderMapper.orderToDto(order)).thenReturn(orderDTO);

        OrderDTO foundOrder = orderService.findOrderById(orderId);

        assertNotNull(foundOrder);
        assertEquals(orderId, foundOrder.getId());
    }

    @Test
    public void testFindOrderByIdNotFound() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.findOrderById(orderId));
    }

    @Test
    public void testAddNewOrder() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setTruckNameId(1L);
        TruckName truckName = new TruckName();
        truckName.setId(1L);

        when(truckNameRepository.findById(orderDTO.getTruckNameId())).thenReturn(Optional.of(truckName));
        when(orderMapper.dtoToOrder(orderDTO)).thenReturn(new Order());
        when(orderRepository.save(any(Order.class))).thenReturn(new Order());
        when(orderMapper.orderToDto(any(Order.class))).thenReturn(orderDTO);

        OrderDTO addedOrder = orderService.addNewOrder(orderDTO);

        assertNotNull(addedOrder);
        assertEquals(orderDTO.getTruckNameId(), addedOrder.getTruckNameId());
    }

    @Test
    public void testAddNewOrderTruckNameNotFound() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setTruckNameId(1L);

        when(truckNameRepository.findById(orderDTO.getTruckNameId())).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.addNewOrder(orderDTO));
    }

    @Test
    public void testUpdateOrder() {
        Long orderId = 1L;
        OrderDTO updatedOrderDTO = new OrderDTO();
        updatedOrderDTO.setId(orderId);

        Order order = new Order();
        order.setId(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.orderToDto(order)).thenReturn(updatedOrderDTO);

        OrderDTO updatedOrder = orderService.updateOrder(orderId, updatedOrderDTO);

        assertNotNull(updatedOrder);
        assertEquals(orderId, updatedOrder.getId());
    }

    @Test
    public void testUpdateOrderNotFound() {
        Long orderId = 1L;
        OrderDTO updatedOrderDTO = new OrderDTO();
        updatedOrderDTO.setId(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.updateOrder(orderId, updatedOrderDTO));
    }

    @Test
    public void testDeleteOrder() {
        Long orderId = 1L;

        when(orderRepository.existsById(orderId)).thenReturn(true);

        assertDoesNotThrow(() -> orderService.deleteOrder(orderId));
        verify(orderRepository, times(1)).deleteById(orderId);
    }

    @Test
    public void testDeleteOrderNotFound() {
        Long orderId = 1L;

        when(orderRepository.existsById(orderId)).thenReturn(false);

        assertThrows(OrderNotFoundException.class, () -> orderService.deleteOrder(orderId));
        verify(orderRepository, never()).deleteById(orderId);
    }
}

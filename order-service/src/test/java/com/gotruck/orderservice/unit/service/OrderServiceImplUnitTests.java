package com.gotruck.orderservice.unit.service;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceImplUnitTests {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderDTO orderDTO;

    @Mock
    private TruckNameRepository truckNameRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Long orderId = 1L;
        when(orderRepository.findById(eq(orderId))).thenReturn(Optional.empty());
    }


    @Test
    public void testGetAllOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        when(orderRepository.findAll()).thenReturn(orders);

        List<OrderDTO> orderDTOList = orderService.getAllOrders();

        assertNotNull(orderDTOList);
        assertFalse(orderDTOList.isEmpty());
        assertEquals(1, orderDTOList.size());
    }

    @Test
    public void testFindOrderById() {
        Long orderId = 1L;

        OrderDTO orderDTO = orderService.findOrderById(orderId);

        assertNull(orderDTO, "OrderDTO should be null because order is not found");
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

        Order savedOrder = new Order();
        savedOrder.setId(1L);
        when(orderMapper.dtoToOrder(orderDTO)).thenReturn(savedOrder);
        when(orderRepository.save(savedOrder)).thenReturn(savedOrder);
        when(orderMapper.orderToDto(savedOrder)).thenReturn(orderDTO);

        OrderDTO result = orderService.addNewOrder(orderDTO);

        assertNotNull(result);
        assertEquals(savedOrder.getId(), result.getId());
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
        updatedOrderDTO.setMaxPayment(1000.0);

        Order existingOrder = new Order();
        existingOrder.setId(orderId);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));

        Order savedOrder = new Order();
        savedOrder.setId(orderId);
        savedOrder.setMaxPayment(updatedOrderDTO.getMaxPayment());
        when(orderRepository.save(existingOrder)).thenReturn(savedOrder);
        when(orderMapper.orderToDto(savedOrder)).thenReturn(updatedOrderDTO);

        OrderDTO result = orderService.updateOrder(orderId, updatedOrderDTO);

        assertNotNull(result);
        assertEquals(savedOrder.getId(), result.getId());
        assertEquals(updatedOrderDTO.getMaxPayment(), result.getMaxPayment());
    }

    @Test
    public void testUpdateOrderNotFound() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.updateOrder(orderId, new OrderDTO()));
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
    }
}


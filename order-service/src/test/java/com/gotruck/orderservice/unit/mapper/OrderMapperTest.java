//package com.gotruck.orderservice.unit.mapper;
//
//import com.gotruck.common.dto.order.AllOrderDTO;
//import com.gotruck.common.dto.order.NewOrderDTO;
//import com.gotruck.common.dto.order.ProcessedOrderDTO;
//import com.gotruck.common.model.enums.order.OrderType;
//import com.gotruck.orderservice.model.Order;
//import com.gotruck.orderservice.mapper.OrderMapper;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDateTime;
//import java.util.Date;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//public class OrderMapperTest {
//
//    private final OrderMapper orderMapper = OrderMapper.INSTANCE;
//
//    @Test
//    void testMapOrderToAllOrderDTO() {
//        Order order = new Order();
//        order.setOrderId(41L);
//        order.setShipperId(17L);
//        order.setMinPayment(300.0);
//        order.setMaxPayment(500.0);
//        order.setProposedPayment(250.0);
//        order.setTruckNameId(4L);
//        order.setTotalWeight(3000.0);
//        order.setDeliveryRoute("Baku to Ganja");
//        order.setPickupLocation("Baku");
//        order.setDeliveryLocation("Ganja");
//        order.setOrderType(OrderType.SINGLE_TRIP);
//        order.setCreatedAt(LocalDateTime.now());
//        order.setPickupDate(new Date());
//        order.setNote("Handle with care");
//
//        AllOrderDTO allOrderDTO = orderMapper.toAllOrderDTO(order);
//
//        assertNotNull(allOrderDTO);
//        assertNotNull(allOrderDTO.getNewOrderRequestDTO());
//        assertNotNull(allOrderDTO.getProcessedOrderDTO());
//
//        NewOrderDTO newOrderDTO = allOrderDTO.getNewOrderRequestDTO();
//        ProcessedOrderDTO processedOrderDTO = allOrderDTO.getProcessedOrderDTO();
//
//        assertEquals(order.getOrderId(), newOrderDTO.getOrderId());
//        assertEquals(order.getShipperId(), newOrderDTO.getShipperId());
//        assertEquals(order.getMinPayment(), newOrderDTO.getMinPayment());
//        assertEquals(order.getMaxPayment(), newOrderDTO.getMaxPayment());
//        assertEquals(order.getProposedPayment(), newOrderDTO.getProposedPayment());
//        assertEquals(order.getTruckNameId(), newOrderDTO.getTruckNameId());
//        assertEquals(order.getTotalWeight(), newOrderDTO.getTotalWeight());
//        assertEquals(order.getDeliveryRoute(), newOrderDTO.getDeliveryRoute());
//        assertEquals(order.getPickupLocation(), newOrderDTO.getPickupLocation());
//        assertEquals(order.getDeliveryLocation(), newOrderDTO.getDeliveryLocation());
//        assertEquals(order.getOrderType(), newOrderDTO.getOrderType());
//        assertEquals(order.getCreatedAt(), newOrderDTO.getCreatedAt());
//        assertEquals(order.getPickupDate(), newOrderDTO.getPickupDate());
//        assertEquals(order.getNote(), newOrderDTO.getNote());
//
//        assertEquals(order.getOrderId(), processedOrderDTO.getOrderId());
//        assertEquals(order.getShipperId(), processedOrderDTO.getShipperId());
//        assertEquals(order.getMinPayment(), processedOrderDTO.getMinPayment());
//        assertEquals(order.getMaxPayment(), processedOrderDTO.getMaxPayment());
//        assertEquals(order.getProposedPayment(), processedOrderDTO.getProposedPayment());
//        assertEquals(order.getTruckNameId(), processedOrderDTO.getTruckNameId());
//        assertEquals(order.getTotalWeight(), processedOrderDTO.getTotalWeight());
//        assertEquals(order.getDeliveryRoute(), processedOrderDTO.getDeliveryRoute());
//        assertEquals(order.getPickupLocation(), processedOrderDTO.getPickupLocation());
//        assertEquals(order.getDeliveryLocation(), processedOrderDTO.getDeliveryLocation());
//        assertEquals(order.getOrderType(), processedOrderDTO.getOrderType());
//        assertEquals(order.getCreatedAt(), processedOrderDTO.getCreatedAt());
//        assertEquals(order.getPickupDate(), processedOrderDTO.getPickupDate());
//        assertEquals(order.getNote(), processedOrderDTO.getNote());
//    }
//}

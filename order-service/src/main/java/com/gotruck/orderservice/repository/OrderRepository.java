package com.gotruck.orderservice.repository;

import com.gotruck.common.model.enums.order.OrderStatus;
import com.gotruck.common.model.enums.order.OrderType;
import com.gotruck.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderType(OrderType orderType);
    List<Order> findByOrderStatus(OrderStatus orderStatus);
}

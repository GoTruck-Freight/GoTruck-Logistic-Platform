package com.gotruck.orderservice.dao.repository;

import com.gotruck.common.model.enums.order.OrderType;
import com.gotruck.orderservice.dao.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByOrderType(OrderType orderType);
}

package com.gotruck.orderservice.repository;

import com.gotruck.orderservice.model.Order;
import com.gotruck.orderservice.model.enums.OrderType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findById(Long id);
    List<Order> findAll();
    void deleteById(Long id);

    List<Order> findByOrderType(OrderType orderType);
}

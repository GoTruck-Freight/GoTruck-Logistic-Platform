package com.gotruck.orderservice.model;

import com.gotruck.common.model.enums.order.OrderStatus;
import com.gotruck.common.model.enums.order.OrderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private Long shipperId;
    private Double minPayment;
    private Double maxPayment;
    private Double proposedPayment;
    private Long truckNameId;
    private Double totalWeight;
    private String deliveryRoute;
    private String pickupLocation;
    private String deliveryLocation;
    @Enumerated(EnumType.STRING)
    @Column(name = "order_type")
    private OrderType orderType;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date pickupDate;
    private String note;
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;
    private LocalDateTime cancelledAt;
    private String rejectReason;
    private String cancelledBy;
    private LocalDateTime acceptedAt;
    private LocalDateTime loadedAt;
    private LocalDateTime inTransitAt;
    private LocalDateTime unloadedAt;
    private LocalDateTime completedAt;
    //    private String costumerFeedback;

}

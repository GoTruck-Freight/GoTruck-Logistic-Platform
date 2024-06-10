package com.gotruck.orderservice.dao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gotruck.common.model.enums.order.OrderType;
import com.gotruck.common.model.order.OrderEntityReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "orders")
@ToString(callSuper = true)
public class OrderEntity extends BaseEntity {
    @Embedded
    private OrderEntityReference references;

    @Column(name = "proposed_payment")
    private BigDecimal proposedPayment;

    @Column(name = "truck_name_id")
    private Long truckNameId;

    @Column(name = "total_weight")
    private BigDecimal totalWeight;

    @Column(name = "delivery_route")
    private String deliveryRoute;

    @Column(name = "pickup_location")
    private String pickupLocation;

    @Column(name = "delivery_location")
    private String deliveryLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type")
    private OrderType orderType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "pickup_date")
    private Date pickupDate;

    @Column(name = "note")
    private String note;

    @Column(name = "is_active")
    private boolean isActive;
}

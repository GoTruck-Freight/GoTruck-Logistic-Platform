package com.gotruck.common.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gotruck.common.model.enums.order.OrderType;
import com.gotruck.common.model.order.OrderEntityReference;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
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

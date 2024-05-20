package com.gotruck.common.dto.order;

import com.gotruck.common.model.enums.order.OrderStatus;
import com.gotruck.common.model.enums.order.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseOrderDTO {
    // Bütün sifarişlər üçün ümumi məlumatları saxlayan baza DTO
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
    private OrderType orderType;
    private LocalDateTime createdAt;
    private Date pickupDate;
    private String note;
    private OrderStatus orderStatus;
}

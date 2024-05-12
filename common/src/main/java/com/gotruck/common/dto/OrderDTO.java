package com.gotruck.common.dto;

import com.gotruck.common.model.OrderType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private Double minPayment;
    private Double maxPayment;
    private Double proposedPayment;
    private Long truckNameId;
    private Double totalWeight;
    //    private String costumerFeedback;
    //    private String orderStatus;
    private String deliveryRoute;
    private String pickupLocation;
    private String deliveryLocation;
    @Enumerated(EnumType.STRING)
    private OrderType orderType;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date pickupDate;
    private String note;
}
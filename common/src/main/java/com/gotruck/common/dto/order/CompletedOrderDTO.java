package com.gotruck.common.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompletedOrderDTO extends UnloadedOrderDTO{
    private LocalDateTime completedAt;

    // Additional fields after order acceptance
//    private PaymentDetailsDTO paymentDetails;
//    private String paymentStatus;
//    private String paymentMethod;
//    private LocalDateTime acceptedAt;
}

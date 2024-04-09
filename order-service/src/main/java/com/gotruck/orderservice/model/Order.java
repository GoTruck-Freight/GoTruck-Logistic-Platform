package com.gotruck.orderservice.model;

import com.gotruck.orderservice.model.enums.OrderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    @Column(name = "order_type")
    private OrderType orderType;
    private LocalDateTime createdAt;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date pickupDate;
    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMinPayment() {
        return minPayment;
    }

    public void setMinPayment(Double minPayment) {
        this.minPayment = minPayment;
    }

    public Double getMaxPayment() {
        return maxPayment;
    }

    public void setMaxPayment(Double maxPayment) {
        this.maxPayment = maxPayment;
    }

    public Double getProposedPayment() {
        return proposedPayment;
    }

    public void setProposedPayment(Double proposedPayment) {
        this.proposedPayment = proposedPayment;
    }

    public Long getTruckNameId() {
        return truckNameId;
    }

    public void setTruckNameId(Long truckNameId) {
        this.truckNameId = truckNameId;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getDeliveryRoute() {
        return deliveryRoute;
    }

    public void setDeliveryRoute(String deliveryRoute) {
        this.deliveryRoute = deliveryRoute;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }
}

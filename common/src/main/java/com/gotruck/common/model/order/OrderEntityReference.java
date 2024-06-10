package com.gotruck.common.model.order;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class OrderEntityReference {
    @Column(name = "map_id")
    private Long mapId;

    @Column(name = "shipper_id")
    private Long shipperId;

    @Column(name = "truck_category_id")
    private Long truckCategoryId;

    @Column(name = "pricing_id")
    private Long pricingId;

    @Column(name = "tracking_id")
    private Long trackingId;

    @Column(name = "driver_id")
    private Long driverId;

    @Column(name = "broker_id")
    private Long brokerId;

    @Column(name = "payment_id")
    private Long paymentId;

    public Long getShipperId() {
        return shipperId;
    }

    public void setShipperId(Long shipperId) {
        this.shipperId = shipperId;
    }
}
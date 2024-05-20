package com.gotruck.common.dto.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllOrderDTO {
    private NewOrderDTO newOrderDTO;    // Sifarişin qəbul edilmədən öncəki məlumatları
    private AcceptedOrderDTO acceptedOrderDTO;  // Sifarişin qəbul edildikdən sonrakı məlumatları
    private LoadedOrderDTO loadedOrderDTO;      // Sifarişin maşına yükləndikdən sonrakı məlumatları
    private InTransitOrderDTO inTransitOrderDTO; // Sifarişin hərəkətdə olduğu haldakı məlumatları
    private UnloadedOrderDTO unloadedOrderDTO;  // Sifarişin boşaldılma halındakı məlumatları
    private CompletedOrderDTO completedOrderDTO; // Sifarişin tam işlənib və tamamlandıqdan sonrakı məlumatları
    private CancelledOrderDTO cancelledOrderDTO;  // Sifarişin rədd edilməsi halında məlumatları
}


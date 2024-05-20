package com.gotruck.common.model.enums.order;

public enum OrderStatus {
    ACTIVE,           // Sifariş yeni yaradılıb və hələ qəbul edilməyib
    ACCEPTED,      // Sifariş qəbul edilib və işlənməyə hazırdır
    LOADED,        // Sifariş maşına yüklənib
    IN_TRANSIT,    // Sifariş çatdırılma yerinə doğru hərəkətdədir
    UNLOADED,      // Sifariş boşaldılıb
    COMPLETED,     // Sifariş tam işlənib və tamamlanıb
    CANCELLED       // Sifariş rədd edilib və qəbul olunmayıb
}


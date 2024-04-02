package com.gotruck.truckcategoryservice.service;

import com.gotruck.truckcategoryservice.dto.TruckNameDTO;

import java.util.List;
import java.util.UUID;

public interface TruckNameService {
    List<TruckNameDTO> getAllTruckNames();
    String getTruckNameById(UUID truckNameId);
    TruckNameDTO addNewTruckName(TruckNameDTO truckNameDTO);
    TruckNameDTO updateTruckName(UUID id, TruckNameDTO truckNameDTO);
    void deleteTruckName(UUID id);

}

package com.gotruck.truckcategoryservice.service;

import com.gotruck.truckcategoryservice.dto.TruckNameDTO;

import java.util.List;

public interface TruckNameService {
    List<String> getAllTruckNames();

    TruckNameDTO getTruckNameById(Long id);

    TruckNameDTO addNewTruckName(TruckNameDTO truckNameDTO);

    TruckNameDTO updateTruckName(Long id, TruckNameDTO truckNameDTO);

    void deleteTruckName(Long id);

}

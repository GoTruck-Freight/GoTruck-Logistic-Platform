package com.gotruck.truckcategoryservice.service;

import com.gotruck.truckcategoryservice.dto.TruckCategoryDTO;

import java.util.List;
import java.util.Map;

public interface TruckCategoryService {
    List<TruckCategoryDTO> getAllTruckCategories();
    TruckCategoryDTO getTruckCategoryById(Long id);
    TruckCategoryDTO addNewTruckCategory(TruckCategoryDTO truckCategoryDTO);
    TruckCategoryDTO updateTruckCategory(Long id, TruckCategoryDTO truckCategoryDTO);

    TruckCategoryDTO patchTruckCategory(Long id, Map<String, Object> fields);


    void deleteTruckCategory(Long id);
}


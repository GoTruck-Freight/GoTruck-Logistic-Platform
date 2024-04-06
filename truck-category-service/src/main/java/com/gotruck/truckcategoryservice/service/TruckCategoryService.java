package com.gotruck.truckcategoryservice.service;

import com.gotruck.truckcategoryservice.dto.TruckCategoryDTO;
import com.gotruck.truckcategoryservice.model.TruckCategory;

import java.util.List;

public interface TruckCategoryService {
    List<TruckCategoryDTO> getAllTruckCategories();
    TruckCategoryDTO getTruckCategoryById(Long id);
    TruckCategoryDTO addNewTruckCategory(TruckCategoryDTO truckCategoryDTO);
    TruckCategoryDTO updateTruckCategory(Long id, TruckCategoryDTO truckCategoryDTO);
    void deleteTruckCategory(Long id);
}


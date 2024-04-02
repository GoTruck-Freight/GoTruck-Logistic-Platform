package com.gotruck.truckcategoryservice.service.Impl;

import com.gotruck.truckcategoryservice.dto.TruckCategoryDTO;
import com.gotruck.truckcategoryservice.dto.TruckNameDTO;
import com.gotruck.truckcategoryservice.exceptions.TruckCategoryNotFoundException;
import com.gotruck.truckcategoryservice.model.TruckCategory;
import com.gotruck.truckcategoryservice.repository.TruckCategoryRepository;
import com.gotruck.truckcategoryservice.service.TruckCategoryService;
import com.gotruck.truckcategoryservice.service.TruckNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TruckCategoryServiceImpl implements TruckCategoryService {
    private final TruckCategoryRepository truckCategoryRepository;
    private final TruckNameService truckNameService;

    @Autowired
    public TruckCategoryServiceImpl(TruckCategoryRepository truckCategoryRepository, TruckNameService truckNameService) {
        this.truckCategoryRepository = truckCategoryRepository;
        this.truckNameService = truckNameService;
    }

    @Override
    public List<TruckCategoryDTO> getAllTruckCategories() {
        List<TruckCategory> truckCategories = truckCategoryRepository.findAll();
        return truckCategories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TruckCategoryDTO getTruckCategoryById(Long id) {
        Optional<TruckCategory> truckCategoryOptional = truckCategoryRepository.findById(id);
        if (truckCategoryOptional.isPresent()){
            TruckCategory truckCategory = truckCategoryOptional.get();
            return getTruckCategoryDTO(truckCategory);
        }
        else{
            throw new TruckCategoryNotFoundException("Truck category not found with id: " +id);
        }
    }

    @Override
    public TruckCategoryDTO getTruckCategoryDTO(TruckCategory truckCategory) {
        TruckCategoryDTO truckCategoryDTO = new TruckCategoryDTO();
        truckCategoryDTO.setId(truckCategory.getId());
        truckCategoryDTO.setDescription(truckCategory.getDescription());
        truckCategoryDTO.setMaxLoadCapacity(truckCategory.getMaxLoadCapacity());
        truckCategoryDTO.setCargoAreaWidth(truckCategory.getCargoAreaWidth());
        truckCategoryDTO.setCargoAreaLength(truckCategory.getCargoAreaLength());
        truckCategoryDTO.setCargoAreaHeight(truckCategory.getCargoAreaHeight());
        truckCategoryDTO.setCargoCubicVolume(truckCategory.getCargoCubicVolume());
//        truckCategoryDTO.setTruckNameId(truckCategory.getTruckNameId());
        truckCategoryDTO.setTruckName(truckCategoryDTO.getTruckName());

        return truckCategoryDTO;
    }

    @Override
    public TruckCategoryDTO addNewTruckCategory(TruckCategoryDTO truckCategoryDTO) {
        TruckCategory newTruckCategory = new TruckCategory();
        newTruckCategory.setDescription(truckCategoryDTO.getDescription());
        newTruckCategory.setMaxLoadCapacity(truckCategoryDTO.getMaxLoadCapacity());
        newTruckCategory.setCargoAreaWidth(truckCategoryDTO.getCargoAreaWidth());
        newTruckCategory.setCargoAreaLength(truckCategoryDTO.getCargoAreaLength());
        newTruckCategory.setCargoAreaHeight(truckCategoryDTO.getCargoAreaHeight());
        newTruckCategory.setCargoCubicVolume(truckCategoryDTO.getCargoCubicVolume());

        // Get the TruckNameDTO from TruckCategoryDTO
        TruckNameDTO truckNameDTO = truckCategoryDTO.getTruckName();
        if (truckNameDTO == null || truckNameDTO.getId() == null) {
            throw new IllegalArgumentException("Truck name DTO or ID cannot be null");
        }

         String truckNameId = truckNameService.getTruckNameById(truckNameDTO.getId());
         newTruckCategory.setTruckNameId(truckNameId);

        TruckCategory savedTruckCategory = truckCategoryRepository.save(newTruckCategory);
        return getTruckCategoryDTO(savedTruckCategory);
    }

    @Override
    public TruckCategoryDTO updateTruckCategory(Long id, TruckCategoryDTO truckCategoryDTO) {
        Optional<TruckCategory> truckCategoryOptional = truckCategoryRepository.findById(id);
        if(truckCategoryOptional.isPresent()){
            TruckCategory existingTruckCategory = truckCategoryOptional.get();
            updateExistingTruckCategory(existingTruckCategory, truckCategoryDTO);

            TruckCategory updatedTruckCategory = truckCategoryRepository.save(existingTruckCategory);
            return getTruckCategoryDTO(updatedTruckCategory);
        }
        else {
            throw new TruckCategoryNotFoundException("Truck category bot found with id: " +id);
        }
    }

    private void updateExistingTruckCategory(TruckCategory existingTruckCategory, TruckCategoryDTO truckCategoryDTO) {
        existingTruckCategory.setDescription(truckCategoryDTO.getDescription());
        existingTruckCategory.setMaxLoadCapacity(truckCategoryDTO.getMaxLoadCapacity());
        existingTruckCategory.setCargoAreaWidth(truckCategoryDTO.getCargoAreaWidth());
        existingTruckCategory.setCargoAreaLength(truckCategoryDTO.getCargoAreaLength());
        existingTruckCategory.setCargoAreaHeight(truckCategoryDTO.getCargoAreaHeight());
        existingTruckCategory.setCargoCubicVolume(truckCategoryDTO.getCargoCubicVolume());
        existingTruckCategory.setTruckNameId(truckCategoryDTO.getTruckNameId());
    }

    @Override
    public void deleteTruckCategory(Long id) {
        truckCategoryRepository.deleteById(id);
    }


    private TruckCategoryDTO convertToDTO(TruckCategory truckCategory) {
        TruckCategoryDTO truckCategoryDTO = new TruckCategoryDTO();
        truckCategoryDTO.setId(truckCategory.getId());
        truckCategoryDTO.setDescription(truckCategory.getDescription());
        truckCategoryDTO.setMaxLoadCapacity(truckCategory.getMaxLoadCapacity());
        truckCategoryDTO.setCargoAreaWidth(truckCategory.getCargoAreaWidth());
        truckCategoryDTO.setCargoAreaLength(truckCategory.getCargoAreaLength());
        truckCategoryDTO.setCargoAreaHeight(truckCategory.getCargoAreaHeight());
        truckCategoryDTO.setCargoCubicVolume(truckCategory.getCargoCubicVolume());
        truckCategoryDTO.setTruckNameId(truckCategoryDTO.getTruckNameId());
        return truckCategoryDTO;
    }


}

package com.gotruck.truckcategoryservice.service.Impl;

import com.gotruck.truckcategoryservice.dto.TruckCategoryDTO;
import com.gotruck.truckcategoryservice.exceptions.TruckCategoryNotFoundException;
import com.gotruck.truckcategoryservice.exceptions.TruckNameNotFoundException;
import com.gotruck.truckcategoryservice.mapper.TruckCategoryMapper;
import com.gotruck.truckcategoryservice.model.TruckCategory;
import com.gotruck.truckcategoryservice.model.TruckName;
import com.gotruck.truckcategoryservice.repository.TruckCategoryRepository;
import com.gotruck.truckcategoryservice.repository.TruckNameRepository;
import com.gotruck.truckcategoryservice.service.TruckCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TruckCategoryServiceImpl implements TruckCategoryService {
    private final TruckCategoryRepository truckCategoryRepository;
    private final TruckNameRepository truckNameRepository;
    private final TruckCategoryMapper truckCategoryMapper;


    @Autowired
    public TruckCategoryServiceImpl(TruckCategoryRepository truckCategoryRepository, TruckNameRepository truckNameRepository, TruckCategoryMapper truckCategoryMapper) {
        this.truckCategoryRepository = truckCategoryRepository;
        this.truckNameRepository = truckNameRepository;
        this.truckCategoryMapper = truckCategoryMapper;
    }

    @Override
    public List<TruckCategoryDTO> getAllTruckCategories() {
        try {
            List<TruckCategory> truckCategories = truckCategoryRepository.findAll();
            return truckCategories.stream()
                    .map(truckCategoryMapper::truckCategoryToDto) // Use the mapper method
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new TruckNameNotFoundException("Error fetching TruckCategories Details");
        }
    }

    @Override
    public TruckCategoryDTO getTruckCategoryById(Long id) {
        Optional<TruckCategory> truckCategoryOptional = truckCategoryRepository.findById(id);
        if (truckCategoryOptional.isPresent()) {
            TruckCategory truckCategory = truckCategoryOptional.get();
            return truckCategoryMapper.truckCategoryToDto(truckCategory);
        } else {
            throw new TruckCategoryNotFoundException("Truck category not found with id: " + id);
        }
    }

    @Override
    public TruckCategoryDTO addNewTruckCategory(TruckCategoryDTO truckCategoryDTO) {
        Optional<TruckName> truckNameOptional = truckNameRepository.findById(truckCategoryDTO.getTruckNameId());
        if (truckNameOptional.isPresent()) {
            TruckCategory newTruckCategory = truckCategoryMapper.dtoToTruckCategory(truckCategoryDTO);

            // Set TruckName ID directly from DTO
            newTruckCategory.setTruckNameId(truckCategoryDTO.getTruckNameId());

            TruckCategory savedTruckCategory = truckCategoryRepository.save(newTruckCategory);
            return truckCategoryMapper.truckCategoryToDto(savedTruckCategory);
        } else {
            throw new TruckNameNotFoundException("Truck name not found with id: " + truckCategoryDTO.getTruckNameId());
        }
    }

    @Override
    public TruckCategoryDTO updateTruckCategory(Long id, TruckCategoryDTO updatedTruckCategoryDTO) {
        Optional<TruckCategory> truckCategoryOptional = truckCategoryRepository.findById(id);
        if (truckCategoryOptional.isPresent()) {
            TruckCategory truckCategory = truckCategoryOptional.get();

            // Update only the fields that are not null in updatedTruckCategoryDTO
            if (Objects.nonNull(updatedTruckCategoryDTO.getDescription())) {
                truckCategory.setDescription(updatedTruckCategoryDTO.getDescription());
            }
            if (Objects.nonNull(updatedTruckCategoryDTO.getMaxLoadCapacity()) && updatedTruckCategoryDTO.getMaxLoadCapacity() > 0) {
                truckCategory.setMaxLoadCapacity(updatedTruckCategoryDTO.getMaxLoadCapacity());
            }
            if (Objects.nonNull(updatedTruckCategoryDTO.getCargoAreaWidth()) && updatedTruckCategoryDTO.getCargoAreaWidth() > 0) {
                truckCategory.setCargoAreaWidth(updatedTruckCategoryDTO.getCargoAreaWidth());
            }
            if (Objects.nonNull(updatedTruckCategoryDTO.getCargoAreaLength()) && updatedTruckCategoryDTO.getCargoAreaLength() > 0) {
                truckCategory.setCargoAreaLength(updatedTruckCategoryDTO.getCargoAreaLength());
            }
            if (Objects.nonNull(updatedTruckCategoryDTO.getCargoAreaHeight()) && updatedTruckCategoryDTO.getCargoAreaHeight() > 0) {
                truckCategory.setCargoAreaHeight(updatedTruckCategoryDTO.getCargoAreaHeight());
            }
            if (Objects.nonNull(updatedTruckCategoryDTO.getCargoCubicVolume()) && updatedTruckCategoryDTO.getCargoCubicVolume() > 0) {
                truckCategory.setCargoCubicVolume(updatedTruckCategoryDTO.getCargoCubicVolume());
            }
            if (Objects.nonNull(updatedTruckCategoryDTO.getTruckNameId())) {
                truckCategory.setTruckNameId(updatedTruckCategoryDTO.getTruckNameId());
            }

            // Save the updated truck category
            TruckCategory updatedTruckCategory = truckCategoryRepository.save(truckCategory);

            // Map and return the DTO
            return truckCategoryMapper.truckCategoryToDto(updatedTruckCategory);
        } else {
            throw new TruckCategoryNotFoundException("Truck category not found with id: " + id);
        }
    }

    @Override
    public void deleteTruckCategory(Long id) {
        if (truckCategoryRepository.existsById(id)) {
            truckCategoryRepository.deleteById(id);
        } else {
            throw new TruckCategoryNotFoundException("Truck category not found with id: " + id);
        }
    }
}
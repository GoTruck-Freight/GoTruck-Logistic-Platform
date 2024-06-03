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
    public TruckCategoryDTO updateTruckCategory(Long id,TruckCategoryDTO truckCategoryDTO) {
        Optional<TruckCategory> truckCategoryOptional = truckCategoryRepository.findById(truckCategoryDTO.getId());
        if (!truckCategoryOptional.isPresent()) {
            throw new TruckCategoryNotFoundException("Truck category not found with id: " + truckCategoryDTO.getId());
        }

        TruckCategory existingTruckCategory = truckCategoryOptional.get();

        // Update the fields of the existing truck category
        existingTruckCategory.setDescription(truckCategoryDTO.getDescription());
        existingTruckCategory.setMaxLoadCapacity(truckCategoryDTO.getMaxLoadCapacity());
        existingTruckCategory.setCargoAreaWidth(truckCategoryDTO.getCargoAreaWidth());
        existingTruckCategory.setCargoAreaLength(truckCategoryDTO.getCargoAreaLength());
        existingTruckCategory.setCargoAreaHeight(truckCategoryDTO.getCargoAreaHeight());
        existingTruckCategory.setCargoCubicVolume(truckCategoryDTO.getCargoCubicVolume());
        existingTruckCategory.setTruckNameId(truckCategoryDTO.getTruckNameId());

        // Save the updated truck category
        TruckCategory updatedTruckCategory = truckCategoryRepository.save(existingTruckCategory);

        // Return the updated truck category DTO
        return truckCategoryMapper.truckCategoryToDto(updatedTruckCategory);
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
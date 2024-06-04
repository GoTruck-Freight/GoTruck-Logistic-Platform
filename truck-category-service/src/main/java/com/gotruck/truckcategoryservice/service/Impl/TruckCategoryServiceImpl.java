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
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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
    public TruckCategoryDTO updateTruckCategory(Long id, TruckCategoryDTO truckCategoryDTO) {
        TruckCategory existingTruckCategory = truckCategoryRepository.findById(id)
                .orElseThrow(() -> new TruckCategoryNotFoundException("Truck category not found with id: " + id));
        truckCategoryMapper.updateTruckCategoryFromDto(truckCategoryDTO, existingTruckCategory);
        existingTruckCategory = truckCategoryRepository.save(existingTruckCategory);
        return truckCategoryMapper.truckCategoryToDto(existingTruckCategory);
    }


    @Override
    public TruckCategoryDTO patchTruckCategory(Long id, Map<String, Object> fields) {
        TruckCategory existingTruckCategory = truckCategoryRepository.findById(id)
                .orElseThrow(() -> new TruckCategoryNotFoundException("Truck category not found with id: " + id));

        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(TruckCategory.class, k);
            if (field != null) {
                field.setAccessible(true);
                Object value = v;
                if (field.getType().equals(Long.class) && v instanceof Integer) {
                    value = Long.valueOf((Integer) v);
                } else if (field.getType().equals(BigDecimal.class) && v instanceof String) {
                    value = new BigDecimal((String) v);
                }
                ReflectionUtils.setField(field, existingTruckCategory, value);
            }
        });

        TruckCategory updatedTruckCategory = truckCategoryRepository.save(existingTruckCategory);
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
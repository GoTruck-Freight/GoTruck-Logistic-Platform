package com.gotruck.truckcategoryservice.service.Impl;

import com.gotruck.truckcategoryservice.dto.TruckNameDTO;
import com.gotruck.truckcategoryservice.exceptions.TruckNameNotFoundException;
import com.gotruck.truckcategoryservice.mapper.TruckNameMapper;
import com.gotruck.truckcategoryservice.model.TruckName;
import com.gotruck.truckcategoryservice.repository.TruckNameRepository;
import com.gotruck.truckcategoryservice.service.TruckNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class TruckNameServiceImpl implements TruckNameService {

    private final TruckNameRepository truckNameRepository;
    private final TruckNameMapper truckNameMapper;

    @Autowired
    public TruckNameServiceImpl(TruckNameRepository truckNameRepository, TruckNameMapper truckNameMapper) {
        this.truckNameRepository = truckNameRepository;
        this.truckNameMapper = truckNameMapper;
    }

    @Override
    public List<String> getAllTruckNames() {
        List<TruckName> truckNames = truckNameRepository.findAll();
        return truckNames.stream()
                .map(TruckName::getName)
                .collect(Collectors.toList());
    }

    @Override
    public TruckNameDTO findTruckNameById(Long id) {
        Optional<TruckName> truckNameOptional = truckNameRepository.findById(id);
        if (truckNameOptional.isPresent()){
            TruckName truckName = truckNameOptional.get();
            return truckNameMapper.truckNameToDto(truckName);
        }
        else {
            throw new TruckNameNotFoundException("Truck name not found with id: " + id);
        }
    }

    @Override
    public TruckNameDTO addNewTruckName(TruckNameDTO truckNameDTO) {
        if (truckNameDTO.getId() != null) {
            throw new IllegalArgumentException("New truck name should not have an ID.");
        }

        if (truckNameDTO.getName() == null || truckNameDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Truck name cannot be null or empty.");
        }

        TruckName truckName = truckNameMapper.dtoToTruckName(truckNameDTO);
        TruckName savedTruckName = truckNameRepository.save(truckName);
        return truckNameMapper.truckNameToDto(savedTruckName);
    }

    @Override
    public TruckNameDTO updateTruckName(Long id, TruckNameDTO truckNameDTO) {
        TruckName existingTruckName = truckNameRepository.findById(id)
                .orElseThrow(() -> new TruckNameNotFoundException("Truck name not found with id: " + id));

        truckNameMapper.updateTruckNameFromDTO(truckNameDTO, existingTruckName);

        TruckName updatedTruckName = truckNameRepository.save(existingTruckName);
        return truckNameMapper.truckNameToDto(updatedTruckName);
    }

    @Override
    public void deleteTruckName(Long id) {
        if(truckNameRepository.existsById(id)){
            truckNameRepository.deleteById(id);
        }
          else {
              throw new TruckNameNotFoundException("Truck name not found with id: " + id);
        }
    }
}
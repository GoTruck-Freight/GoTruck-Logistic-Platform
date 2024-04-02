package com.gotruck.truckcategoryservice.service.Impl;

import com.gotruck.truckcategoryservice.dto.TruckNameDTO;
import com.gotruck.truckcategoryservice.exceptions.TruckNameNotFoundException;
import com.gotruck.truckcategoryservice.model.TruckName;
import com.gotruck.truckcategoryservice.repository.TruckNameRepository;
import com.gotruck.truckcategoryservice.service.TruckNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class TruckNameServiceImpl implements TruckNameService {

    private final TruckNameRepository truckNameRepository;

    @Autowired
    public TruckNameServiceImpl(TruckNameRepository truckNameRepository) {
        this.truckNameRepository = truckNameRepository;
    }

    @Override
    public List<TruckNameDTO> getAllTruckNames() {
        List<TruckName> truckNames = truckNameRepository.findAll();
        return truckNames.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public String getTruckNameById(UUID truckNameId) {
        if (truckNameId == null) {
            throw new IllegalArgumentException("Truck name ID cannot be null");
        }

        Optional<TruckName> truckNameOptional = truckNameRepository.findById(truckNameId);
        if (truckNameOptional.isPresent()) {
            TruckName truckName = truckNameOptional.get();
            return truckName.getName(); // return TruckName name
        } else {
            throw new TruckNameNotFoundException("Truck name not found with id: " + truckNameId);
        }
    }

    @Override
    public TruckNameDTO addNewTruckName(TruckNameDTO truckNameDTO) {
        TruckName truckName = convertToEntity(truckNameDTO);
        TruckName savedTruckName = truckNameRepository.save(truckName);
        return convertToDTO(savedTruckName);
    }

    @Override
    public TruckNameDTO updateTruckName(UUID id, TruckNameDTO truckNameDTO) {
        Optional<TruckName> truckNameOptional = truckNameRepository.findById(id);
        if (truckNameOptional.isPresent()) {
            TruckName existingTruckName = truckNameOptional.get();
            existingTruckName.setName(truckNameDTO.getName());

            TruckName updatedTruckName = truckNameRepository.save(existingTruckName);
            return convertToDTO(updatedTruckName);
        } else {
            throw new TruckNameNotFoundException("Truck name not found with id: " + id);
        }
    }

    @Override
    public void deleteTruckName(UUID id) {
        truckNameRepository.deleteById(id);
    }

    private TruckNameDTO convertToDTO(TruckName truckName) {
        TruckNameDTO truckNameDTO = new TruckNameDTO();
        truckNameDTO.setId(truckName.getId());
        truckNameDTO.setName(truckName.getName());
        return truckNameDTO;
    }

    private TruckName convertToEntity(TruckNameDTO truckNameDTO) {
        TruckName truckName = new TruckName();
        truckName.setName(truckNameDTO.getName());
        return truckName;
    }
}

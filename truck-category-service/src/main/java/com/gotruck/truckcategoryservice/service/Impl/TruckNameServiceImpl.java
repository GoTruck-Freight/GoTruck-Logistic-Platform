package com.gotruck.truckcategoryservice.service.Impl;

import com.gotruck.truckcategoryservice.dto.TruckNameDTO;
import com.gotruck.truckcategoryservice.model.TruckName;
import com.gotruck.truckcategoryservice.repository.TruckNameRepository;
import com.gotruck.truckcategoryservice.service.TruckNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class TruckNameServiceImpl implements TruckNameService {

    private final TruckNameRepository truckNameRepository;
    @Autowired
    public TruckNameDTO truckNameDTO;

    @Autowired
    public TruckNameServiceImpl(TruckNameRepository truckNameRepository, TruckNameDTO truckNameDTO) {
        this.truckNameRepository = truckNameRepository;
        this.truckNameDTO = truckNameDTO;
    }


    @Override
    public List<String> getAllTruckNames() {
        List<TruckName> truckNames = truckNameRepository.findAll();
        return truckNames.stream()
                .map(TruckName::getName)
                .collect(Collectors.toList());

//        return truckNames.stream()
//                .map(truckName -> {
//                    TruckNameDTO dto = new TruckNameDTO();
//                    dto.setId(truckName.getId());
//                    dto.setName(truckName.getName());
//                    return dto;
//                })
//                .collect(Collectors.toList());
    }

    @Override
    public TruckNameDTO findTruckNameById(Long id) {
        TruckName truckName = truckNameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Truck Name not found"));
        return new TruckNameDTO(truckName.getId(), truckName.getName());
    }

    @Override
    public TruckNameDTO addNewTruckName(TruckNameDTO truckNameDTO) {
        TruckName truckName = new TruckName();
        truckName.setId(truckNameDTO.getId()); // Assuming ID is generated automatically
        truckName.setName(truckNameDTO.getName());
        TruckName savedTruckName = truckNameRepository.save(truckName);
        return new TruckNameDTO(savedTruckName.getId(), savedTruckName.getName());
    }

    @Override
    public TruckNameDTO updateTruckName(Long id, TruckNameDTO truckNameDTO) {
        TruckName existingTruckName = truckNameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Truck Name not found"));
        existingTruckName.setName(truckNameDTO.getName());
        TruckName updatedTruckName = truckNameRepository.save(existingTruckName);
        return new TruckNameDTO(updatedTruckName.getId(), updatedTruckName.getName());
    }


    @Override
    public void deleteTruckName(Long id) {
        truckNameRepository.deleteById(id);
    }

}

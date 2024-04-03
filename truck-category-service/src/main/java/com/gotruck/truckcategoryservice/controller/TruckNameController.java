package com.gotruck.truckcategoryservice.controller;

import com.gotruck.truckcategoryservice.dto.TruckNameDTO;
import com.gotruck.truckcategoryservice.service.TruckNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/truck-names")
public class TruckNameController {

    private final TruckNameService truckNameService;

    @Autowired
    public TruckNameController(TruckNameService truckNameService) {
        this.truckNameService = truckNameService;
    }

    @GetMapping
    public List<String> getAllTruckNames(){
        return truckNameService.getAllTruckNames();
    }

    @PostMapping
    public TruckNameDTO addNewTruckName(@RequestBody TruckNameDTO truckNameDTO) {
        return truckNameService.addNewTruckName(truckNameDTO);
    }

    @PutMapping("/{id}")
    public TruckNameDTO updateTruckName(@PathVariable Long id, @RequestBody TruckNameDTO truckNameDTO) {
        return truckNameService.updateTruckName(id, truckNameDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTruckName(@PathVariable Long id) {
        truckNameService.deleteTruckName(id);
    }

}

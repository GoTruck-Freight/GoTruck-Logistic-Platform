package com.gotruck.truckcategoryservice.controller;

import com.gotruck.truckcategoryservice.dto.TruckCategoryDTO;
import com.gotruck.truckcategoryservice.exceptions.TruckCategoryNotFoundException;
import com.gotruck.truckcategoryservice.service.TruckCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/truck-category")
public class TruckCategoryController {
    private final TruckCategoryService truckCategoryService;

    @Autowired
    public TruckCategoryController(TruckCategoryService truckCategoryService) {
        this.truckCategoryService = truckCategoryService;
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<TruckCategoryDTO> getTruckCategoryById(@PathVariable Long id) {
            TruckCategoryDTO truckCategoryDTO = truckCategoryService.getTruckCategoryById(id);
            return ResponseEntity.ok(truckCategoryDTO);
    }

//    @GetMapping("/getAll")
//    public ResponseEntity<List<TruckCategoryDTO>> getAllTruckCategories() {
//        List<TruckCategoryDTO> truckCategories = truckCategoryService.getAllTruckCategories();
//        return new ResponseEntity<>(truckCategories, HttpStatus.OK);
//    }

    @PostMapping("/add")
    public ResponseEntity<TruckCategoryDTO> addNewTruckCategory(@RequestBody TruckCategoryDTO truckCategoryDTO) {
        TruckCategoryDTO savedTruckCategoryDTO = truckCategoryService.addNewTruckCategory(truckCategoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTruckCategoryDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TruckCategoryDTO> updateTruckCategory(
            @PathVariable Long id, @RequestBody TruckCategoryDTO truckCategoryDTO) {
        TruckCategoryDTO updatedTruckCategoryDTO = truckCategoryService.updateTruckCategory(id, truckCategoryDTO);
        return ResponseEntity.ok(updatedTruckCategoryDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTruckCategory(@PathVariable Long id) {
        truckCategoryService.deleteTruckCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


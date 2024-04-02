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

    @GetMapping("/{id}")
    public ResponseEntity<TruckCategoryDTO> getTruckCategoryById(@PathVariable Long id) {
        try {
            TruckCategoryDTO truckCategoryDTO = truckCategoryService.getTruckCategoryById(id);
            return ResponseEntity.ok(truckCategoryDTO);
        } catch (TruckCategoryNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TruckCategoryDTO> addNewTruckCategory(@RequestBody TruckCategoryDTO truckCategoryDTO) {
        try {
            TruckCategoryDTO savedTruckCategoryDTO = truckCategoryService.addNewTruckCategory(truckCategoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTruckCategoryDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TruckCategoryDTO> updateTruckCategory(
            @PathVariable Long id, @RequestBody TruckCategoryDTO truckCategoryDTO) {
        try {
            TruckCategoryDTO updatedTruckCategoryDTO = truckCategoryService.updateTruckCategory(id, truckCategoryDTO);
            return ResponseEntity.ok(updatedTruckCategoryDTO);
        } catch (TruckCategoryNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<TruckCategoryDTO>> getAllTruckCategories() {
        List<TruckCategoryDTO> truckCategories = truckCategoryService.getAllTruckCategories();
        return new ResponseEntity<>(truckCategories, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTruckCategory(@PathVariable Long id) {
        truckCategoryService.deleteTruckCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}


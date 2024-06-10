package com.gotruck.truckcategoryservice.controller;

import com.gotruck.truckcategoryservice.model.dto.TruckCategoryDTO;
import com.gotruck.truckcategoryservice.service.TruckCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;


@RestController
@RequestMapping("api/v1/truck-categories")
public class TruckCategoryController {
    private final TruckCategoryService truckCategoryService;

    @Autowired
    public TruckCategoryController(TruckCategoryService truckCategoryService) {
        this.truckCategoryService = truckCategoryService;
    }

    @GetMapping("/{id}")
    public TruckCategoryDTO getTruckCategoryById(@PathVariable Long id) {
            return truckCategoryService.getTruckCategoryById(id);
    }

    @GetMapping()
    public List<TruckCategoryDTO> getAllTruckCategories() {
        return truckCategoryService.getAllTruckCategories();
    }

    @PostMapping()
    @ResponseStatus(CREATED)
    public TruckCategoryDTO addNewTruckCategory(@Valid @RequestBody TruckCategoryDTO truckCategoryDTO) {
        return truckCategoryService.addNewTruckCategory(truckCategoryDTO);
    }

    @PutMapping("/{id}")
    public TruckCategoryDTO updateTruckCategory(
            @PathVariable Long id, @Valid @RequestBody TruckCategoryDTO truckCategoryDTO) {
            return truckCategoryService.updateTruckCategory(id, truckCategoryDTO);
    }

    @PatchMapping("/{id}")
    public TruckCategoryDTO patchTruckCategory(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        return truckCategoryService.patchTruckCategory(id, fields);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteTruckCategory(@PathVariable Long id) {
        truckCategoryService.deleteTruckCategory(id);
    }
}


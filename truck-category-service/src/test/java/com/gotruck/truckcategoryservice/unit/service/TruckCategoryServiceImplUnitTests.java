package com.gotruck.truckcategoryservice.unit.service;

import com.gotruck.truckcategoryservice.dto.TruckCategoryDTO;
import com.gotruck.truckcategoryservice.mapper.TruckCategoryMapper;
import com.gotruck.truckcategoryservice.model.TruckCategory;
import com.gotruck.truckcategoryservice.model.TruckName;
import com.gotruck.truckcategoryservice.repository.TruckCategoryRepository;
import com.gotruck.truckcategoryservice.repository.TruckNameRepository;
import com.gotruck.truckcategoryservice.service.Impl.TruckCategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TruckCategoryServiceImplUnitTests {

    @Mock
    private TruckCategoryRepository truckCategoryRepository;

    @Mock
    private TruckNameRepository truckNameRepository;

    @Mock
    private TruckCategoryMapper truckCategoryMapper;

    @InjectMocks
    private TruckCategoryServiceImpl truckCategoryService;

    @Mock
    private TruckCategoryDTO testCategoryDTO;

    @BeforeEach
    public void setUp() {
        testCategoryDTO = new TruckCategoryDTO();
        testCategoryDTO.setId(1L);
        testCategoryDTO.setDescription("Test Category");
        testCategoryDTO.setMaxLoadCapacity(1000.0);
        // Set other properties as needed
    }

    @Test
    public void testGetAllTruckCategories() {
        when(truckCategoryRepository.findAll()).thenReturn(Collections.singletonList(new TruckCategory()));
        when(truckCategoryMapper.truckCategoryToDto(any())).thenReturn(testCategoryDTO);

        List<TruckCategoryDTO> result = truckCategoryService.getAllTruckCategories();

        assertEquals(1, result.size());
        assertEquals(testCategoryDTO, result.get(0));
    }

    @Test
    public void testGetTruckCategoryById() {
        when(truckCategoryRepository.findById(1L)).thenReturn(Optional.of(new TruckCategory()));
        when(truckCategoryMapper.truckCategoryToDto(any())).thenReturn(testCategoryDTO);

        TruckCategoryDTO result = truckCategoryService.getTruckCategoryById(1L);

        assertEquals(testCategoryDTO, result);
    }

    @Test
    public void testAddNewTruckCategory() {
        when(truckNameRepository.findById(testCategoryDTO.getTruckNameId())).thenReturn(Optional.of(new TruckName()));
        when(truckCategoryMapper.dtoToTruckCategory(any())).thenReturn(new TruckCategory());
        when(truckCategoryMapper.truckCategoryToDto(any())).thenReturn(testCategoryDTO);
        when(truckCategoryRepository.save(any())).thenReturn(new TruckCategory());

        TruckCategoryDTO result = truckCategoryService.addNewTruckCategory(testCategoryDTO);

        assertEquals(testCategoryDTO, result);
    }

    @Test
    public void testUpdateTruckCategory() {
        TruckCategory updatedCategory = new TruckCategory();
        updatedCategory.setId(1L);
        updatedCategory.setDescription("Updated Category");

        when(truckCategoryRepository.findById(1L)).thenReturn(Optional.of(new TruckCategory()));
        when(truckCategoryRepository.save(any())).thenReturn(updatedCategory);
        when(truckCategoryMapper.truckCategoryToDto(updatedCategory)).thenReturn(testCategoryDTO);

        TruckCategoryDTO result = truckCategoryService.updateTruckCategory(1L, testCategoryDTO);

        assertEquals("Test Category", result.getDescription());
    }

    @Test
    public void testDeleteTruckCategory() {
        when(truckCategoryRepository.existsById(1L)).thenReturn(true);

        truckCategoryService.deleteTruckCategory(1L);

        verify(truckCategoryRepository).deleteById(1L);
    }
}
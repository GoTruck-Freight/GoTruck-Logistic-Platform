package com.gotruck.truckcategoryservice.unit.service;

import com.gotruck.truckcategoryservice.model.dto.TruckCategoryDTO;
import com.gotruck.truckcategoryservice.exceptions.TruckCategoryNotFoundException;
import com.gotruck.truckcategoryservice.exceptions.TruckNameNotFoundException;
import com.gotruck.truckcategoryservice.mapper.TruckCategoryMapper;
import com.gotruck.truckcategoryservice.dao.entity.TruckCategory;
import com.gotruck.truckcategoryservice.dao.entity.TruckName;
import com.gotruck.truckcategoryservice.dao.repository.TruckCategoryRepository;
import com.gotruck.truckcategoryservice.dao.repository.TruckNameRepository;
import com.gotruck.truckcategoryservice.service.Impl.TruckCategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


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
        testCategoryDTO.setMaxLoadCapacity(BigDecimal.valueOf(1000.0));
        // Set other properties as needed
    }

    @Test
    public void testGetAllTruckCategories_Success() {
        when(truckCategoryRepository.findAll()).thenReturn(Collections.singletonList(new TruckCategory()));
        when(truckCategoryMapper.truckCategoryToDto(any())).thenReturn(testCategoryDTO);

        List<TruckCategoryDTO> result = truckCategoryService.getAllTruckCategories();

        assertEquals(1, result.size());
        assertEquals(testCategoryDTO, result.get(0));
    }

    @Test
    public void testGetTruckCategoryById_Success() {
        when(truckCategoryRepository.findById(1L)).thenReturn(Optional.of(new TruckCategory()));
        when(truckCategoryMapper.truckCategoryToDto(any())).thenReturn(testCategoryDTO);

        TruckCategoryDTO result = truckCategoryService.getTruckCategoryById(1L);

        assertEquals(testCategoryDTO, result);
    }

    @Test
    void testGetTruckCategoryById_NotFound() {
        // Setup
        when(truckCategoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Assertion
        assertThrows(TruckCategoryNotFoundException.class, () -> truckCategoryService.getTruckCategoryById(1L));
    }

    @Test
    public void testAddNewTruckCategory_Success() {
        when(truckNameRepository.findById(testCategoryDTO.getTruckNameId())).thenReturn(Optional.of(new TruckName()));
        when(truckCategoryMapper.dtoToTruckCategory(any())).thenReturn(new TruckCategory());
        when(truckCategoryMapper.truckCategoryToDto(any())).thenReturn(testCategoryDTO);
        when(truckCategoryRepository.save(any())).thenReturn(new TruckCategory());

        TruckCategoryDTO result = truckCategoryService.addNewTruckCategory(testCategoryDTO);

        assertEquals(testCategoryDTO, result);
    }

    @Test
    void testAddNewTruckCategory_TruckNameNotFound() {
        // Setup
        when(truckNameRepository.findById(testCategoryDTO.getTruckNameId())).thenReturn(Optional.empty());

        // Assertion
        assertThrows(TruckNameNotFoundException.class, () -> truckCategoryService.addNewTruckCategory(testCategoryDTO));
    }

    @Test
    public void testUpdateTruckCategory_Success() {
        // Given
        Long id = 1L;
        TruckCategoryDTO updatedTruckCategoryDTO = new TruckCategoryDTO();
        updatedTruckCategoryDTO.setDescription("Updated Description");

        TruckCategory existingTruckCategory = new TruckCategory();
        existingTruckCategory.setId(id);
        existingTruckCategory.setDescription("Existing Description");

        when(truckCategoryRepository.findById(id)).thenReturn(Optional.of(existingTruckCategory));
        when(truckCategoryRepository.save(any(TruckCategory.class))).thenReturn(existingTruckCategory);
        when(truckCategoryMapper.truckCategoryToDto(any(TruckCategory.class))).thenReturn(updatedTruckCategoryDTO);

        // When
        TruckCategoryDTO result = truckCategoryService.updateTruckCategory(id, updatedTruckCategoryDTO);

        // Then
        verify(truckCategoryRepository, times(1)).findById(id);
        verify(truckCategoryRepository, times(1)).save(any(TruckCategory.class));
        verify(truckCategoryMapper, times(1)).truckCategoryToDto(any(TruckCategory.class));

        assertEquals(updatedTruckCategoryDTO.getDescription(), result.getDescription());
    }

    @Test
    void testUpdateTruckCategory_NotFound() {
        // Given
        Long id = 1L;
        TruckCategoryDTO updatedTruckCategoryDTO = new TruckCategoryDTO();
        updatedTruckCategoryDTO.setDescription("Updated Description");

        // Setup
        when(truckCategoryRepository.findById(id)).thenReturn(Optional.empty());

        // Assertion
        assertThrows(TruckCategoryNotFoundException.class, () -> truckCategoryService.updateTruckCategory(id, updatedTruckCategoryDTO));
    }

    @Test
    public void testDeleteTruckCategory_Success() {
        when(truckCategoryRepository.existsById(1L)).thenReturn(true);

        truckCategoryService.deleteTruckCategory(1L);

        verify(truckCategoryRepository).deleteById(1L);
    }

    @Test
    void testDeleteTruckCategory_NotFound() {
        // Setup
        when(truckCategoryRepository.existsById(1L)).thenReturn(false);

        // Assertion
        assertThrows(TruckCategoryNotFoundException.class, () -> truckCategoryService.deleteTruckCategory(1L));
    }
}

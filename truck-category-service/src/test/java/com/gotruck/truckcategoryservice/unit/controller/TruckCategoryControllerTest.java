package com.gotruck.truckcategoryservice.unit.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gotruck.truckcategoryservice.controller.TruckCategoryController;
import com.gotruck.truckcategoryservice.dto.TruckCategoryDTO;
import com.gotruck.truckcategoryservice.service.TruckCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TruckCategoryController.class)
class TruckCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TruckCategoryService truckCategoryService;

    private static final String BASE_PATH = "/api/v1/truck-categories";

    private TruckCategoryDTO testTruckCategory;

    @BeforeEach
    void setUp() {
        testTruckCategory = new TruckCategoryDTO();
        testTruckCategory.setId(1L);
        testTruckCategory.setDescription("Test Truck Category");
        testTruckCategory.setMaxLoadCapacity(1000.0);
        testTruckCategory.setCargoAreaWidth(3.5);
        testTruckCategory.setCargoAreaLength(8.0);
        testTruckCategory.setCargoAreaHeight(2.5);
        testTruckCategory.setCargoCubicVolume(70.0);
        testTruckCategory.setTruckNameId(1L);
    }

    @Test
    void testGetTruckCategoryById_Success() throws Exception{
        when(truckCategoryService.getTruckCategoryById(1L)).thenReturn(testTruckCategory);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH + "/" + testTruckCategory.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Test Truck Category"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.maxLoadCapacity").value(1000.0));

        verify(truckCategoryService, times(1)).getTruckCategoryById(testTruckCategory.getId());
    }

    @Test
    void testGetTruckCategoryById_BadRequest_InvalidId() throws Exception {
        // Test for bad request when ID is invalid

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH + "/{id}", "invalidId"))
                .andExpect(status().isBadRequest());

        verify(truckCategoryService, never()).getTruckCategoryById(anyLong());
    }

    @Test
    void testGetAllTruckCategories_Success() throws Exception{
        // Setup
        when(truckCategoryService.getAllTruckCategories()).thenReturn(Collections.singletonList(testTruckCategory));

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Test Truck Category"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].maxLoadCapacity").value(1000.0));

        verify(truckCategoryService, times(1)).getAllTruckCategories();
    }

    @Test
    void testAddNewTruckCategory_Success() throws Exception{
        when(truckCategoryService.addNewTruckCategory(any(TruckCategoryDTO.class))).thenReturn(testTruckCategory);

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTruckCategory)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));

        verify(truckCategoryService,times(1)).addNewTruckCategory(any(TruckCategoryDTO.class));
    }

    @Test
    void testAddNewTruckCategory_BadRequest_MissingPayload() throws Exception {
        // Test for bad request when payload is missing

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(truckCategoryService, never()).addNewTruckCategory(any(TruckCategoryDTO.class));
    }

    @Test
    void testAddNewTruckCategory_BadRequest_InvalidContentType() throws Exception {
        // Test for bad request when content type is invalid

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_PATH)
                        .content(objectMapper.writeValueAsString(testTruckCategory)))
                .andExpect(status().isUnsupportedMediaType());

        verify(truckCategoryService, never()).addNewTruckCategory(any(TruckCategoryDTO.class));
    }

    @Test
    void testAddNewTruckCategory_BadRequest_InvalidPayload() throws Exception {
        // Test for bad request when payload is invalid

        TruckCategoryDTO invalidPayload = new TruckCategoryDTO();
        // Set invalid payload values here

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPayload)))
                .andExpect(status().isBadRequest());

        verify(truckCategoryService, never()).addNewTruckCategory(any(TruckCategoryDTO.class));
    }

    @Test
    void testUpdateTruckCategory_Success() throws Exception {
        testTruckCategory.setDescription("Updated description");

        // Mock the behavior of the service layer
        when(truckCategoryService.updateTruckCategory(anyLong(), any(TruckCategoryDTO.class)))
                .thenReturn(testTruckCategory);

        // Perform the PUT request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_PATH + "/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testTruckCategory)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Updated description"));

        verify(truckCategoryService, times(1)).updateTruckCategory(anyLong(), any(TruckCategoryDTO.class));
    }

    @Test
    void testUpdateTruckCategory_BadRequest_InvalidPayload() throws Exception {
        // Test for bad request when payload is invalid

        TruckCategoryDTO invalidPayload = new TruckCategoryDTO();
        // Set invalid payload values here

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_PATH + "/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPayload)))
                .andExpect(status().isBadRequest());

        verify(truckCategoryService, never()).updateTruckCategory(anyLong(), any(TruckCategoryDTO.class));
    }

    @Test
    void testUpdateTruckCategory_BadRequest_InvalidId() throws Exception {
        // Test for bad request when ID is invalid

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_PATH + "/{id}", "invalidId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTruckCategory)))
                .andExpect(status().isBadRequest());

        verify(truckCategoryService, never()).updateTruckCategory(anyLong(), any(TruckCategoryDTO.class));
    }

    @Test
    void testUpdateTruckCategory_BadRequest_MissingPayload() throws Exception {
        // Test for bad request when payload is missing

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_PATH + "/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(truckCategoryService, never()).updateTruckCategory(anyLong(), any(TruckCategoryDTO.class));
    }

    @Test
    void testDeleteTruckCategory_Success() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_PATH + "/" + testTruckCategory.getId()))
                .andExpect(status().isNoContent());

        verify(truckCategoryService, times(1)).deleteTruckCategory(eq(1L));
    }

    @Test
    void testDeleteTruckCategory_BadRequest_InvalidId() throws Exception {
        // Test for bad request when ID is invalid

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_PATH + "/{id}", "invalidId"))
                .andExpect(status().isBadRequest());

        verify(truckCategoryService, never()).deleteTruckCategory(anyLong());
    }
}

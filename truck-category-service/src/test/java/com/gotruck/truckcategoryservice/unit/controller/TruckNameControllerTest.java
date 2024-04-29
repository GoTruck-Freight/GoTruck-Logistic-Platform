package com.gotruck.truckcategoryservice.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gotruck.truckcategoryservice.controller.TruckNameController;
import com.gotruck.truckcategoryservice.dto.TruckNameDTO;
import com.gotruck.truckcategoryservice.service.TruckNameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = TruckNameController.class)
class TruckNameControllerTest {

    private static final String BASE_PATH = "/api/v1/truck-names";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private TruckNameService truckNameService;

    @Test
    void testGetAllTruckNames_Success() throws Exception {
//        Setup
        when(truckNameService.getAllTruckNames()).thenReturn(List.of("Truck1", "Truck2"));

//        Test
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2));

        verify(truckNameService, times(1)).getAllTruckNames();
    }

    @Test
    void testGetTruckNameById_Success() throws Exception {
//        Setup
        Long id =1L;
        TruckNameDTO truckNameDTO = new TruckNameDTO(id, "Truck1");

        when(truckNameService.getTruckNameById(id)).thenReturn(truckNameDTO);

//        Test
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH + "/{id}", id))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Truck1"));

        verify(truckNameService, times(1)).getTruckNameById(1L);
    }

    @Test
    void testAddNewTruckName_Success() throws Exception {
//        Setup
        TruckNameDTO truckNameDTO = new TruckNameDTO();
        truckNameDTO.setName("Truck1");
        when(truckNameService.addNewTruckName(any(TruckNameDTO.class))).thenReturn(truckNameDTO);

//        Test
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(truckNameDTO)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Truck1"));

        verify(truckNameService, times(1)).addNewTruckName(any(TruckNameDTO.class));
    }

    @Test
    void testUpdateTruckName_Success() throws Exception {
        // Prepare test data
        Long truckNameId = 1L;
        String updatedName = "Updated Truck Name";
        TruckNameDTO updatedTruckNameDTO = new TruckNameDTO(truckNameId, updatedName);

        // Mock service behavior
        when(truckNameService.updateTruckName(anyLong(), any(TruckNameDTO.class)))
                .thenReturn(updatedTruckNameDTO);

        // Perform the PUT request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_PATH + "/{id}", truckNameId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTruckNameDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(truckNameId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(updatedName));

        verify(truckNameService,times(1)).updateTruckName(anyLong(), any(TruckNameDTO.class));
    }

    @Test
    void testDeleteTruckName_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_PATH + "/1"))
                .andExpect(status().isNoContent());

        verify(truckNameService, times(1)).deleteTruckName(1L);
    }

    @Test
    void testDeleteTruckName_NotFound() throws Exception {
        Long id = 1L;

        // Mock the behavior of deleteTruckName to do nothing
        doNothing().when(truckNameService).deleteTruckName(id);

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_PATH + "/{id}", id))
                .andExpect(status().isNoContent());

        verify(truckNameService, times(1)).deleteTruckName(id);
    }
}
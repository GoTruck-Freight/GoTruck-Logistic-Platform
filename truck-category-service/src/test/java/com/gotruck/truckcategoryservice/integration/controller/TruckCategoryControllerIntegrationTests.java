package com.gotruck.truckcategoryservice.integration.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gotruck.truckcategoryservice.dto.TruckCategoryDTO;
import com.gotruck.truckcategoryservice.service.TruckCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
//import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilders;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TruckCategoryControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TruckCategoryService truckCategoryService;

    @Autowired
    private TruckCategoryDTO testCategoryDTO;

    @BeforeEach
    public void setUp() {
//        testCategoryDTO = new TruckCategoryDTO();
        testCategoryDTO.setId(1L);
        testCategoryDTO.setDescription("Test Category");
        testCategoryDTO.setMaxLoadCapacity(1000.0);
        // Set other properties as needed
    }

    @Test
    public void testGetTruckCategoryById() throws Exception {
        when(truckCategoryService.getTruckCategoryById(1L)).thenReturn(testCategoryDTO);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/truck-categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Test Category"));

        resultActions.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testAddNewTruckCategory() throws Exception {
        when(truckCategoryService.addNewTruckCategory(any(TruckCategoryDTO.class))).thenReturn(testCategoryDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/truck-categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCategoryDTO)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Test Category"));
    }

    @Test
    public void testUpdateTruckCategory() throws Exception {
        when(truckCategoryService.updateTruckCategory(any(Long.class), any(testCategoryDTO.getClass()))).thenReturn(testCategoryDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/truck-categories/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCategoryDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Test Category"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.maxLoadCapacity").value(1000.0))
                // Diğer alanları kontrol edin...
                .andDo(print());
    }


    @Test
    public void testDeleteTruckCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/truck-categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}

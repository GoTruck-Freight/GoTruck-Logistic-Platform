package com.gotruck.truckcategoryservice.unit.mapper;

import com.gotruck.truckcategoryservice.dto.TruckCategoryDTO;
import com.gotruck.truckcategoryservice.mapper.TruckCategoryMapper;
import com.gotruck.truckcategoryservice.model.TruckCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class TruckCategoryMapperTest {

    @Mock
    private TruckCategoryMapper truckCategoryMapper;
    @Mock
    private TruckCategoryDTO truckCategoryDTO;
    @Mock
    private TruckCategory truckCategory;

    @BeforeEach
    void setUp() {
        truckCategoryMapper= Mappers.getMapper(TruckCategoryMapper.class);

//        Mock data
        truckCategoryDTO = new TruckCategoryDTO();
        truckCategoryDTO.setDescription("Truck Category 1");
        truckCategoryDTO.setMaxLoadCapacity(1000.0);
        truckCategoryDTO.setCargoAreaWidth(2.5);
        truckCategoryDTO.setCargoAreaLength(6.0);
        truckCategoryDTO.setCargoAreaHeight(2.0);
        truckCategoryDTO.setCargoCubicVolume(30.0);
        truckCategoryDTO.setTruckNameId(1L);

        truckCategory = new TruckCategory();
        truckCategory.setDescription("Truck Category 1");
        truckCategory.setMaxLoadCapacity(1000.0);
        truckCategory.setCargoAreaWidth(2.5);
        truckCategory.setCargoAreaLength(6.0);
        truckCategory.setCargoAreaHeight(2.0);
        truckCategory.setCargoCubicVolume(30.0);
        truckCategory.setTruckNameId(1L);
    }

    @Test
    void testDtoToTruckCategory() {
//        Act
        TruckCategory result = truckCategoryMapper.dtoToTruckCategory(truckCategoryDTO);

//        Assert
        assertNotNull(result);
        assertEquals(truckCategoryDTO.getDescription(), result.getDescription());
        assertEquals(truckCategoryDTO.getMaxLoadCapacity(),result.getMaxLoadCapacity());
        assertEquals(truckCategoryDTO.getCargoAreaWidth(),result.getCargoAreaWidth());
        assertEquals(truckCategoryDTO.getCargoAreaLength(),result.getCargoAreaLength());
        assertEquals(truckCategoryDTO.getCargoAreaHeight(),result.getCargoAreaHeight());
        assertEquals(truckCategoryDTO.getCargoCubicVolume(),result.getCargoCubicVolume());
        assertEquals(truckCategoryDTO.getTruckNameId(),result.getTruckNameId());
    }

    @Test
    void testTruckCategoryToDto() {
//        Act
        TruckCategoryDTO result = truckCategoryMapper.truckCategoryToDto(truckCategory);

//        Assert
        assertNotNull(result);
        assertEquals(truckCategory.getDescription(), result.getDescription());
        assertEquals(truckCategory.getMaxLoadCapacity(), result.getMaxLoadCapacity());
        assertEquals(truckCategory.getCargoAreaWidth(), result.getCargoAreaWidth());
        assertEquals(truckCategory.getCargoAreaLength(), result.getCargoAreaLength());
        assertEquals(truckCategory.getCargoAreaHeight(), result.getCargoAreaHeight());
        assertEquals(truckCategory.getCargoCubicVolume(), result.getCargoCubicVolume());
        assertEquals(truckCategory.getTruckNameId(), result.getTruckNameId());
    }
}
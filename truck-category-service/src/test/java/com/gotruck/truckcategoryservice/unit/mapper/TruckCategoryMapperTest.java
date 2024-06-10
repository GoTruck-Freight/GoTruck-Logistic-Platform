package com.gotruck.truckcategoryservice.unit.mapper;

import com.gotruck.truckcategoryservice.model.dto.TruckCategoryDTO;
import com.gotruck.truckcategoryservice.mapper.TruckCategoryMapper;
import com.gotruck.truckcategoryservice.dao.entity.TruckCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;

import java.math.BigDecimal;

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
        truckCategoryDTO.setMaxLoadCapacity(BigDecimal.valueOf(1000.0));
        truckCategoryDTO.setCargoAreaWidth(BigDecimal.valueOf(2.5));
        truckCategoryDTO.setCargoAreaLength(BigDecimal.valueOf(6.0));
        truckCategoryDTO.setCargoAreaHeight(BigDecimal.valueOf(2.0));
        truckCategoryDTO.setCargoCubicVolume(BigDecimal.valueOf(30.0));
        truckCategoryDTO.setTruckNameId(1L);

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
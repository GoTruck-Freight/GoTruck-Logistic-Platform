package com.gotruck.truckcategoryservice.unit.mapper;

import com.gotruck.truckcategoryservice.model.dto.TruckNameDTO;
import com.gotruck.truckcategoryservice.mapper.TruckNameMapper;
import com.gotruck.truckcategoryservice.dao.entity.TruckName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TruckNameMapperTest {

    @Mock
    private final TruckNameMapper truckNameMapper = Mappers.getMapper(TruckNameMapper.class);
    @Mock
    private TruckNameDTO truckNameDTO;
    @Mock
    private TruckName truckName;


    @BeforeEach
    void setUp(){
//        Mock data setup
        truckNameDTO = new TruckNameDTO();
        truckNameDTO.setId(1L);
        truckNameDTO.setName("TruckName1");

        truckName = new TruckName();
        truckName.setId(1L);
        truckName.setName("TruckName1");

    }
    @Test
    void testDtoToTruckName(){
//        Act
        TruckName result = truckNameMapper.dtoToTruckName(truckNameDTO);

//        Assert
        assertNotNull(result);
        assertEquals(truckNameDTO.getId(), result.getId());
        assertEquals(truckNameDTO.getName(),result.getName());
    }
    @Test
    void testTruckNameToDto(){
//        Act
        TruckNameDTO result = truckNameMapper.truckNameToDto(truckName);

//        Assert
        assertNotNull(result);
        assertEquals(truckName.getId(),result.getId());
        assertEquals(truckName.getName(),result.getName());
    }

    @Test
    void testUpdateTruckNameFromDto(){
//        Arrange
        TruckNameDTO updateDTO = new TruckNameDTO();
        updateDTO.setName("UpdatedTruckName");

//        Act
        truckNameMapper.updateTruckNameFromDTO(updateDTO, truckName);

//        Assert
        assertEquals(updateDTO.getName(), truckName.getName());
    }
}

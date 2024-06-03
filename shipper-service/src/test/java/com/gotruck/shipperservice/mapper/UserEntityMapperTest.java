package com.gotruck.shipperservice.mapper;

import com.gotruck.shipperservice.model.dto.UserDto;
import com.gotruck.shipperservice.dao.entity.UserEntity;
import com.gotruck.shipperservice.model.enums.AccountStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserEntityMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testToUser() {
        UserDto userDto = new UserDto();
        userDto.setCompanyName("Yeni MMC");
        userDto.setContactName("Yeni User");
        userDto.setEmail("TestShipperOrder@gmail.com");
        userDto.setPassword("Test_Shipper_Order_16");
        userDto.setPhoneNumber("05566655");
        userDto.setImage("/images/profileImages/default-shipper-user.jpeg");
        userDto.setAccountStatus(AccountStatus.ENABLED);

        UserEntity userEntity = userMapper.toUserEntity(userDto);

        assertNotNull(userEntity);
        assertEquals("Yeni MMC", userEntity.getCompanyName());
        assertEquals("Yeni User", userEntity.getContactName());
        assertEquals("TestShipperOrder@gmail.com", userEntity.getEmail());
        assertEquals("Test_Shipper_Order_16", userEntity.getPassword());
        assertEquals("05566655", userEntity.getPhoneNumber());
        assertEquals("/images/profileImages/default-shipper-user.jpeg", userEntity.getImage());
        assertEquals(AccountStatus.ENABLED, userEntity.getAccountStatus());
    }
}

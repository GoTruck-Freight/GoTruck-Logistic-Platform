package com.gotruck.shipperservice.mapper;

import com.gotruck.shipperservice.dto.UserDto;
import com.gotruck.shipperservice.model.User;
import com.gotruck.shipperservice.model.enums.AccountStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserMapperTest {

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

        User user = userMapper.toUser(userDto);

        assertNotNull(user);
        assertEquals("Yeni MMC", user.getCompanyName());
        assertEquals("Yeni User", user.getContactName());
        assertEquals("TestShipperOrder@gmail.com", user.getEmail());
        assertEquals("Test_Shipper_Order_16", user.getPassword());
        assertEquals("05566655", user.getPhoneNumber());
        assertEquals("/images/profileImages/default-shipper-user.jpeg", user.getImage());
        assertEquals(AccountStatus.ENABLED, user.getAccountStatus());
    }
}

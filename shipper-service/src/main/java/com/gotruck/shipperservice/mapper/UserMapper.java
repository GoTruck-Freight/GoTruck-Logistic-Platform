package com.gotruck.shipperservice.mapper;

import com.gotruck.shipperservice.dto.UserDto;
import com.gotruck.shipperservice.dto.UserProfile;
import com.gotruck.shipperservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDto toUserDto(User user);
//    @Mapping(source = "userDto.companyName", target = "companyName")
//    @Mapping(source = "userDto.contactName", target = "contactName")
//    @Mapping(source = "userDto.email", target = "email")
//    @Mapping(source = "userDto.password", target = "password")
//    @Mapping(source = "userDto.phoneNumber", target = "phoneNumber")
    User toUser(UserDto userDto);
    UserProfile toUserProfile(User user);
}

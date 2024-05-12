package com.gotruck.shipperservice.mapper;

import com.gotruck.shipperservice.dto.UserDto;
import com.gotruck.shipperservice.dto.UserProfile;
import com.gotruck.shipperservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDto toUserDto(User user);
    User toUser(UserDto userDto);
    UserProfile toUserProfile(User user);
}

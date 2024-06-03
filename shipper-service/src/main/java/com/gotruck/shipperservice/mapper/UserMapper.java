package com.gotruck.shipperservice.mapper;

import com.gotruck.shipperservice.model.dto.UserDto;
import com.gotruck.shipperservice.model.dto.UserProfile;
import com.gotruck.shipperservice.dao.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(UserEntity userEntity);

    @Mapping(source = "companyName", target = "companyName")
    @Mapping(source = "contactName", target = "contactName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "password", target = "password")
    UserEntity toUserEntity(UserDto userDto);

    UserProfile toUserProfile(UserEntity userEntity);
}

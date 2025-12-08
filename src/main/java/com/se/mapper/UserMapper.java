package com.se.mapper;

import com.se.dto.UserDto;
import com.se.entities.UserEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper{

    UserDto toDto(UserEntity entity);
    UserEntity toEntity(UserDto dto);

    List<UserDto> toDtoList(List<UserEntity> entities);
    List<UserEntity> toEntities(List<UserDto> dtos);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UserDto dto, @MappingTarget UserEntity entity);
}

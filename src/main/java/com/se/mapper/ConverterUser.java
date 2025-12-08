package com.se.mapper;

import com.se.dto.UserDto;
import com.se.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConverterUser {

    public static UserDto entityToDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setAge(userEntity.getAge());
        userDto.setProfession(userEntity.getProfession());
        userDto.setVersion(userEntity.getVersion());
        return userDto;
    }

    public static UserEntity setUser(UserEntity userEntity, UserDto userDto) {

        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setProfession(userDto.getProfession());

        return userEntity;
    }

    public static UserEntity dtoToEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDto.getId());
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setAge(userDto.getAge());
        userEntity.setProfession(userDto.getProfession());
        userEntity.setVersion(userDto.getVersion());
        return userEntity;
    }

    public static List<UserDto> convertToDtoList(List<UserEntity> users) {
        return users.stream()
                .map(ConverterUser::entityToDto)
                .collect(Collectors.toList());
    }
}

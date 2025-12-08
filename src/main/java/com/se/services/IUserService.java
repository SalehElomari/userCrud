package com.se.services;

import com.se.dto.UserDto;

import java.util.List;

public interface IUserService {

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    UserDto addUser(UserDto userDto);

    void deleteUser(Long id);

    UserDto updateUser(Long id, UserDto userDto);


}

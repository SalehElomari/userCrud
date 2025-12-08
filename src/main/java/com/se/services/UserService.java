package com.se.services;

import com.se.dto.UserDto;
import com.se.mapper.UserMapper;
import com.se.repository.UserRepository;
import com.se.entities.UserEntity;
import com.se.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class UserService implements IUserService {


    @Autowired
    public UserRepository userDao;

    private final UserMapper mapper;

    public UserService(UserRepository userDao, UserMapper mapper) {
        this.userDao = userDao;
        this.mapper = mapper;
    }


    public ArrayList<UserDto> users;


    @Override
    public List<UserDto> getAllUsers() {

        List<UserEntity> userEntities = userDao.findAll();
        return mapper.toDtoList(userEntities);
    }

    @Override
    public UserDto getUserById(Long id) {
        UserEntity userEntity = userDao.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User " + id + " not found"));

        return mapper.toDto(userEntity);
    }


    @Override
    @Transactional
    public UserDto addUser(UserDto userDto) {

        UserEntity userEntity = mapper.toEntity(userDto);
        userEntity = userDao.save(userEntity);
        return mapper.toDto(userEntity);

    }

    @Override
    public void deleteUser(Long id) {

        if (!userDao.existsById(id)) {
            throw new RuntimeException("User not found");
        }

        userDao.deleteById(id);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        UserEntity user = userDao.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        mapper.updateEntityFromDto(userDto,user);
        return  mapper.toDto(user);
    }
}

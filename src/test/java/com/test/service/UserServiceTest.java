package com.test.service;

import com.se.dto.UserDto;
import com.se.entities.UserEntity;
import com.se.exceptions.UserNotFoundException;
import com.se.mapper.UserMapper;
import com.se.repository.UserRepository;
import com.se.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userDao;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserService userService;

    private UserEntity userEntity;
    private UserDto userDto;

    @BeforeEach
    void setup() {
        userEntity = new UserEntity();
        userEntity.setId(1L);

        userDto = new UserDto();
        userDto.setId(1L);
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        List<UserEntity> entities = Arrays.asList(userEntity);

        when(userDao.findAll()).thenReturn(entities);
        when(mapper.toDtoList(anyList())).thenReturn(Arrays.asList(userDto));

        List<UserDto> result = userService.getAllUsers();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);

        verify(userDao).findAll();
        verify(mapper).toDtoList(anyList());
    }

    @Test
    void getUserById_shouldReturnUser_whenExists() {
        when(userDao.findById(1L)).thenReturn(Optional.of(userEntity));
        when(mapper.toDto(any(UserEntity.class))).thenReturn(userDto);

        UserDto result = userService.getUserById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);

        verify(userDao).findById(1L);
        verify(mapper).toDto(any(UserEntity.class));
    }

    @Test
    void getUserById_shouldThrowException_whenNotFound() {
        when(userDao.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(1L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("1");

        verify(userDao).findById(1L);
    }

    @Test
    void addUser_shouldSaveAndReturnDto() {
        when(mapper.toEntity(any(UserDto.class))).thenReturn(userEntity);
        when(userDao.save(any(UserEntity.class))).thenReturn(userEntity);
        when(mapper.toDto(any(UserEntity.class))).thenReturn(userDto);

        UserDto result = userService.addUser(userDto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);

        verify(mapper).toEntity(any(UserDto.class));
        verify(userDao).save(any(UserEntity.class));
        verify(mapper).toDto(any(UserEntity.class));
    }

    @Test
    void deleteUser_shouldDelete_whenUserExists() {
        when(userDao.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userDao).existsById(1L);
        verify(userDao).deleteById(1L);
    }

    @Test
    void deleteUser_shouldThrowException_whenNotFound() {
        when(userDao.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> userService.deleteUser(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found");

        verify(userDao).existsById(1L);
        verify(userDao, never()).deleteById(anyLong());
    }

    @Test
    void updateUser_shouldUpdateUser_whenExists() {
        when(userDao.findById(1L)).thenReturn(Optional.of(userEntity));
        doNothing().when(mapper).updateEntityFromDto(any(UserDto.class), any(UserEntity.class));
        when(mapper.toDto(any(UserEntity.class))).thenReturn(userDto);

        UserDto result = userService.updateUser(1L, userDto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);

        verify(userDao).findById(1L);
        verify(mapper).updateEntityFromDto(any(UserDto.class), any(UserEntity.class));
        verify(mapper).toDto(any(UserEntity.class));
    }

    @Test
    void updateUser_shouldThrowException_whenNotFound() {
        when(userDao.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.updateUser(1L, userDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found");

        verify(userDao).findById(1L);
        verify(mapper, never()).updateEntityFromDto(any(), any());
    }
}

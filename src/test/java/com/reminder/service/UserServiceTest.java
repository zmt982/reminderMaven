package com.reminder.service;

import com.reminder.model.User;
import com.reminder.model.dto.UserDto;
import com.reminder.model.mapper.UserMapper;
import com.reminder.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    //    @Mock
//    private ReminderMapper reminderMapper;
    @InjectMocks
    private UserService userService;
    private UserDto userDto;
    private User user;

    @BeforeEach
    void init() {
        userDto = new UserDto();
        userDto.setUsername("John");
        userDto.setEmail("john2024@gmail.com");
        userDto.setTelegram("@john-dev");

        user = new User();
        user.setId(1L);
        user.setUsername("John");
        user.setEmail("john2024@gmail.com");
        user.setTelegram("@john-dev");
    }

    @Test
    void getAll() {
        List<User> users = Collections.singletonList(user);
        List<UserDto> expected = Collections.singletonList(userDto);

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDto(users)).thenReturn(expected);

        List<UserDto> result = userService.getAll();

        assertEquals(expected, result);

        verify(userRepository.findAll());
        verify(userMapper).toDto(users);
    }

    @Test
    void getById() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.getById(1L);

        assertEquals(userDto, result);

        verify(userRepository).findById(1L);
        verify(userMapper).toDto(user);
    }

    @Test
    void addUser() {
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.addUser(userDto);

        assertEquals(userDto, result);

        verify(userMapper).toEntity(userDto);
        verify(userRepository).save(user);
        verify(userMapper).toDto(user);
    }

    @Test
    void updateById() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
//        when(reminderMapper.toEntity());
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.updateById(1L, userDto);

        assertEquals(userDto, result);

        verify(userRepository).findById(1L);
//        verify(reminderMapper.toEntity());
        verify(userMapper).toDto(user);
    }

    @Test
    void deleteUserById() {
        userService.deleteUserById(1L);

        verify(userRepository).deleteById(1L);
    }
}
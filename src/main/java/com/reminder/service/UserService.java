package com.reminder.service;

import com.reminder.model.User;
import com.reminder.model.dto.UserDto;
import com.reminder.model.mapper.ReminderMapper;
import com.reminder.model.mapper.UserMapper;
import com.reminder.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ReminderMapper reminderMapper;

    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        return userMapper.toDto(users);
    }

    public UserDto getById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        User userToFind = userOptional.orElseThrow(() -> new RuntimeException("No user found"));
        return userMapper.toDto(userToFind);
    }

    public UserDto addUser(UserDto addDto) {
        User userToAdd = userMapper.toEntity(addDto);
        userToAdd = userRepository.save(userToAdd);
        return userMapper.toDto(userToAdd);
    }

    public UserDto updateById(Long id, UserDto updateDto) {
        Optional<User> userToFind = userRepository.findById(id);
        User userToUpdate = userToFind.orElseThrow(() -> new RuntimeException("No user found"));
        userToUpdate.setUsername(updateDto.getUsername());
        userToUpdate.setEmail(updateDto.getEmail());
        userToUpdate.setTelegram(updateDto.getTelegram());
        userToUpdate.setReminders(reminderMapper.toEntity(updateDto.getReminders()));
        return userMapper.toDto(userToUpdate);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
package com.reminder.service;

import com.reminder.exception.ReminderNotFoundException;
import com.reminder.model.Reminder;
import com.reminder.model.dto.ReminderDto;
import com.reminder.model.mapper.ReminderMapper;
import com.reminder.repository.ReminderRepository;
import com.reminder.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReminderService {
    private final ReminderRepository reminderRepository;
    private final ReminderMapper reminderMapper;
    private final UserRepository userRepository;

    public ReminderDto addReminder(ReminderDto addDto) {
        Reminder reminderToAdd = reminderMapper.toEntity(addDto);
        reminderToAdd = reminderRepository.save(reminderToAdd);
        return reminderMapper.toDto(reminderToAdd);
    }

    public void deleteReminderById(Long id) {
        reminderRepository.deleteById(id);
    }

    public ReminderDto updateReminderById(Long id, ReminderDto updateDto) {
        Optional<Reminder> reminderToFind = reminderRepository.findById(id);
        Reminder reminderToUpdate = reminderToFind.orElseThrow(() -> new RuntimeException("No reminder found"));
        reminderToUpdate.setTitle(updateDto.getTitle());
        reminderToUpdate.setDescription(updateDto.getDescription());
        reminderToUpdate.setRemind(updateDto.getRemind());
        reminderToUpdate.setUser(userRepository.findById(updateDto.getUserId()).orElseThrow(() ->
                new RuntimeException("User not found")));
        return reminderMapper.toDto(reminderToUpdate);
    }

    public Page<ReminderDto> getRemindersByTitle(String title, Pageable pageable) {
        Optional<Page<Reminder>> optionalRemindersByTitle = Optional.ofNullable(reminderRepository
                .findRemindersByTitle(title, pageable));
        Page<Reminder> remindersByTitle = optionalRemindersByTitle.orElseThrow(() ->
                new RuntimeException("No reminders found by title"));
        return reminderMapper.toDto(remindersByTitle);
    }

    public Page<ReminderDto> getRemindersByDescription(String description, Pageable pageable) {
        Optional<Page<Reminder>> optionalRemindersByDescription = Optional.ofNullable(reminderRepository
                .findRemindersByDescription(description, pageable));
        Page<Reminder> remindersByDescription = optionalRemindersByDescription.orElseThrow(() ->
                new RuntimeException("No reminders found by description"));
        return reminderMapper.toDto(remindersByDescription);
    }

    public Page<ReminderDto> getRemindersByRemind(LocalDateTime remind, Pageable pageable) {
        Optional<Page<Reminder>> optionalRemindersByRemind = Optional.ofNullable(reminderRepository
                .findRemindersByRemind(remind, pageable));
        Page<Reminder> remindersByRemind = optionalRemindersByRemind.orElseThrow(() ->
                new RuntimeException("No reminders found by remind"));
        return reminderMapper.toDto(remindersByRemind);
    }

    public Page<ReminderDto> getRemindersSortedByTitle(Pageable pageable) {
        Page<Reminder> remindersSortedByTitle = reminderRepository.findRemindersSortedByTitle(pageable);
        if (!remindersSortedByTitle.hasContent()) {
            throw new ReminderNotFoundException("Reminders not found");
        }
        return reminderMapper.toDto(remindersSortedByTitle);
    }

    public Page<ReminderDto> getRemindersSortedByRemind(Pageable pageable) {
        Page<Reminder> remindersSortedByRemind = reminderRepository.findRemindersSortedByRemind(pageable);
        if (!remindersSortedByRemind.hasContent()) {
            throw new ReminderNotFoundException("Reminders not found");
        }
        return reminderMapper.toDto(remindersSortedByRemind);
    }

    public Page<ReminderDto> getRemindersByTitleContaining(String title, Pageable pageable) {
        Page<Reminder> remindersByTitleContaining = reminderRepository.findRemindersByTitleContaining(title, pageable);
        if (!remindersByTitleContaining.hasContent()) {
            throw new ReminderNotFoundException("Reminders not found");
        }
        return reminderMapper.toDto(remindersByTitleContaining);
    }
}
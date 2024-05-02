package com.reminder.service;

import com.reminder.model.Reminder;
import com.reminder.model.dto.ReminderDto;
import com.reminder.model.mapper.ReminderMapper;
import com.reminder.repository.ReminderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReminderServiceTest {
    @Mock
    private ReminderRepository reminderRepository;
    @Mock
    private ReminderMapper reminderMapper;
    //    @Mock
//    private UserRepository userRepository;
    @InjectMocks
    private ReminderService reminderService;
    private ReminderDto reminderDto;
    private Reminder reminder;
    private Pageable pageable;
    private Page<Reminder> reminders;
    private Page<ReminderDto> expected;


    @BeforeEach
    void init(TestInfo testInfo) {
        reminderDto = new ReminderDto();
        reminderDto.setTitle("titleTest");
        reminderDto.setDescription("descriptionTest");
        reminderDto.setRemind(LocalDateTime.of(2024, Month.MAY, 9, 00, 00));

        reminder = new Reminder();
        reminder.setId(1L);
        reminder.setTitle("titleTest");
        reminder.setDescription("descriptionTest");
        reminder.setRemind(LocalDateTime.of(2024, Month.MAY, 9, 00, 00));

        if (testInfo != null) {
            List<String> methodNames = Arrays.asList("getRemindersByName", "getRemindersByDescription",
                    "getRemindersByDate", "getRemindersByTime", "getRemindersSortedByTitle",
                    "getRemindersSortedByRemind", "getRemindersByTitleContaining");
            if (methodNames.contains(testInfo.getTestMethod().get().getName())) {
                pageable = PageRequest.of(0, 4);
                reminders = (Page<Reminder>) Collections.singletonList(reminder);
                expected = (Page<ReminderDto>) Collections.singletonList(reminderDto);
            }
        }
    }

    @Test
    void addReminder() {
        when(reminderMapper.toEntity(reminderDto)).thenReturn(reminder);
        when(reminderRepository.save(reminder)).thenReturn(reminder);
        when(reminderMapper.toDto(reminder)).thenReturn(reminderDto);

        ReminderDto result = reminderService.addReminder(reminderDto);

        assertEquals(reminderDto, result);

        verify(reminderMapper).toEntity(reminderDto);
        verify(reminderRepository).save(reminder);
        verify(reminderMapper).toDto(reminder);
    }

    @Test
    void deleteReminderById() {
        reminderService.deleteReminderById(1L);

        verify(reminderRepository).deleteById(1L);
    }

    @Test
    void updateReminderById() {
        when(reminderRepository.findById(1L)).thenReturn(Optional.ofNullable(reminder));
        when(reminderMapper.toDto(reminder)).thenReturn(reminderDto);

        ReminderDto result = reminderService.updateReminderById(1L, reminderDto);

        assertEquals(reminderDto, result);

        verify(reminderRepository).findById(1L);
        verify(reminderMapper).toDto(reminder);
    }

    @Test
    void getRemindersByName() {
        when(reminderRepository.findRemindersByTitle(reminderDto.getTitle(), pageable)).thenReturn(reminders);
        when(reminderMapper.toDto(reminders)).thenReturn(expected);

        Page<ReminderDto> result = reminderService.getRemindersByTitle(reminderDto.getTitle(), pageable);

        assertEquals(expected, result);

        verify(reminderRepository).findRemindersByTitle(reminderDto.getTitle(), pageable);
        verify(reminderMapper).toDto(reminders);
    }

    @Test
    void getRemindersByDescription() {
        when(reminderRepository.findRemindersByDescription(reminderDto.getDescription(), pageable))
                .thenReturn(reminders);
        when(reminderMapper.toDto(reminders)).thenReturn(expected);

        Page<ReminderDto> result = reminderService.getRemindersByDescription(reminderDto.getDescription(), pageable);

        assertEquals(expected, result);

        verify(reminderRepository).findRemindersByDescription(reminderDto.getDescription(), pageable);
        verify(reminderMapper).toDto(reminders);
    }

    @Test
    void getRemindersByRemind() {
        when(reminderRepository.findRemindersByRemind(reminderDto.getRemind(), pageable)).thenReturn(reminders);
        when(reminderMapper.toDto(reminders)).thenReturn(expected);

        Page<ReminderDto> result = reminderService.getRemindersByRemind(reminderDto.getRemind(), pageable);

        assertEquals(expected, result);

        verify(reminderRepository).findRemindersByRemind(reminderDto.getRemind(), pageable);
        verify(reminderMapper).toDto(reminders);
    }

    @Test
    void getRemindersSortedByTitle() {
        when(reminderRepository.findRemindersSortedByTitle(pageable)).thenReturn(reminders);
        when(reminderMapper.toDto(reminders)).thenReturn(expected);

        Page<ReminderDto> result = reminderService.getRemindersSortedByTitle(pageable);

        assertEquals(expected, result);

        verify(reminderRepository).findRemindersSortedByTitle(pageable);
        verify(reminderMapper).toDto(reminders);
    }

    @Test
    void getRemindersSortedByRemind() {
        when(reminderRepository.findRemindersSortedByRemind(pageable)).thenReturn(reminders);
        when(reminderMapper.toDto(reminders)).thenReturn(expected);

        Page<ReminderDto> result = reminderService.getRemindersSortedByRemind(pageable);

        assertEquals(expected, result);

        verify(reminderRepository).findRemindersSortedByRemind(pageable);
        verify(reminderMapper).toDto(reminders);
    }

    @Test
    void getRemindersByTitleContaining() {
        when((reminderRepository.findRemindersByTitleContaining(reminderDto.getTitle(), pageable)))
                .thenReturn(reminders);
        when(reminderMapper.toDto(reminders)).thenReturn(expected);

        Page<ReminderDto> result = reminderService.getRemindersByTitleContaining(reminderDto.getTitle(), pageable);

        assertEquals(expected, result);

        verify(reminderRepository).findRemindersByTitleContaining(reminderDto.getTitle(), pageable);
        verify(reminderMapper).toDto(reminders);
    }
}
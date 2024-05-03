package com.reminder.controller;

import com.reminder.model.dto.ReminderDto;
import com.reminder.service.ReminderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/reminder/")
@AllArgsConstructor
public class ReminderController {
    private final ReminderService reminderService;

    @PostMapping("/reminders")
    public ResponseEntity<ReminderDto> addReminder(@RequestBody ReminderDto addDto) {
        return ResponseEntity.ok(reminderService.addReminder(addDto));
    }

    @DeleteMapping("/reminders/{id}")
    public ResponseEntity<Long> deleteReminderById(@PathVariable Long id) {
        reminderService.deleteReminderById(id);
        return ResponseEntity.ok(id);
    }

    @PutMapping("/reminders/{id}")
    public ResponseEntity<ReminderDto> updateReminderById(@PathVariable Long id, @RequestBody ReminderDto updateDto) {
        return ResponseEntity.ok(reminderService.updateReminderById(id, updateDto));
    }

    @GetMapping("/reminders/title/{title}")
    public ResponseEntity<Page<ReminderDto>> getRemindersByTitle(@PathVariable String title,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(reminderService.getRemindersByTitle(title, PageRequest.of(page, size)));
    }

    @GetMapping("/reminders/description/{description}")
    public ResponseEntity<Page<ReminderDto>> getRemindersByDescription(@PathVariable String description,
                                                                       @RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(reminderService.getRemindersByDescription(description, PageRequest.of(page, size)));
    }

    @GetMapping("/reminders/remind/{remind}")
    public ResponseEntity<Page<ReminderDto>> getRemindersByRemind(@PathVariable LocalDateTime remind,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(reminderService.getRemindersByRemind(remind, PageRequest.of(page, size)));
    }

    @GetMapping("/reminders/sorted-by-title")
    public ResponseEntity<Page<ReminderDto>> getRemindersSortedByTitle(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(reminderService.getRemindersSortedByTitle(PageRequest.of(page, size)));
    }

    @GetMapping("/reminders/sorted-by-remind")
    public ResponseEntity<Page<ReminderDto>> getRemindersSortedByRemind(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(reminderService.getRemindersSortedByRemind(PageRequest.of(page, size)));
    }

    @GetMapping("/reminders/sorted-by-title-contain/{string}")
    public ResponseEntity<Page<ReminderDto>> getRemindersByTitleContainig(@PathVariable String string,
                                                                          @RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(reminderService.getRemindersByTitleContaining(string, PageRequest.of(page, size)));
    }
}
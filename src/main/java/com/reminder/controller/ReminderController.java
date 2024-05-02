package com.reminder.controller;

import com.reminder.model.dto.ReminderDto;
import com.reminder.service.ReminderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/reminder/")
@AllArgsConstructor
public class ReminderController {
    private final ReminderService reminderService;

    @PostMapping("/create")
    public ResponseEntity<ReminderDto> addReminder(@RequestBody ReminderDto addDto) {
        return ResponseEntity.ok(reminderService.addReminder(addDto));
    }

    @DeleteMapping("/delete{id}")
    public ResponseEntity<Long> deleteReminderById(@PathVariable Long id) {
        reminderService.deleteReminderById(id);
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReminderDto> updateReminderById(@PathVariable Long id, @RequestBody ReminderDto updateDto) {
        return ResponseEntity.ok(reminderService.updateReminderById(id, updateDto));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Page<ReminderDto>> getRemindersByTitle(String title, Pageable pageable) {
        return ResponseEntity.ok(reminderService.getRemindersByTitle(title, pageable));
    }

    @GetMapping("/description/{description}")
    public ResponseEntity<Page<ReminderDto>> getRemindersByDescription(String description, Pageable pageable) {
        return ResponseEntity.ok(reminderService.getRemindersByDescription(description, pageable));
    }

    @GetMapping("/remind/{remind}")
    public ResponseEntity<Page<ReminderDto>> getRemindersByRemind(LocalDateTime remind, Pageable pageable) {
        return ResponseEntity.ok(reminderService.getRemindersByRemind(remind, pageable));
    }


    @GetMapping("/sorted-by-title")
    public ResponseEntity<Page<ReminderDto>> getRemindersSortedByTitle(Pageable pageable) {
        return ResponseEntity.ok(reminderService.getRemindersSortedByTitle(pageable));
    }

    @GetMapping("/sorted-by-remind")
    public ResponseEntity<Page<ReminderDto>> getRemindersSortedByRemind(Pageable pageable) {
        return ResponseEntity.ok(reminderService.getRemindersSortedByRemind(pageable));
    }

    @GetMapping("/sorted-by-title-contain/{string}")
    public ResponseEntity<Page<ReminderDto>> getRemindersByTitleContainig(String string, Pageable pageable) {
        return ResponseEntity.ok(reminderService.getRemindersByTitleContaining(string, pageable));
    }
}
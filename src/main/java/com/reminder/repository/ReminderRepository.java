package com.reminder.repository;

import com.reminder.model.Reminder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    Page<Reminder> findRemindersByTitle(String title, Pageable pageable);

    Page<Reminder> findRemindersByDescription(String description, Pageable pageable);

    Page<Reminder> findRemindersByRemind(LocalDateTime remind, Pageable pageable);

    @Query("SELECT r FROM Reminder r ORDER BY r.title")
    Page<Reminder> findRemindersSortedByTitle(Pageable pageable);

    @Query("SELECT r FROM Reminder r ORDER BY r.remind")
    Page<Reminder> findRemindersSortedByRemind(Pageable pageable);

    @Query("SELECT r FROM Reminder r WHERE LOWER(r.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    Page<Reminder> findRemindersByTitleContaining(String title, Pageable pageable);
}
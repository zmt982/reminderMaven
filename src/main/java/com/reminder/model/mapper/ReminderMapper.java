package com.reminder.model.mapper;

import com.reminder.model.Reminder;
import com.reminder.model.dto.ReminderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Mapper(componentModel = "spring")
public interface ReminderMapper {
    @Mapping(source = "user.id", target = "userId")
    ReminderDto toDto(Reminder reminder);

    @Mapping(source = "userId", target = "user.id")
    Reminder toEntity(ReminderDto dto);

    List<ReminderDto> toDto(List<Reminder> reminders);

    default Page<ReminderDto> toDto(Page<Reminder> reminders) {
        return reminders.map(this::toDto);
    }

    Set<Reminder> toEntity(Set<ReminderDto> reminders);

    default Page<Reminder> toEntity(Page<ReminderDto> reminders) {
        return reminders.map(this::toEntity);
    }
}
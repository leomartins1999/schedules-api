package com.iscte.mei.ads.schedules.api.services;

import com.iscte.mei.ads.schedules.api.entities.Schedule;
import com.iscte.mei.ads.schedules.api.models.WriteSchedule;
import com.iscte.mei.ads.schedules.api.repositories.SchedulesRepository;
import com.iscte.mei.ads.schedules.api.utils.IterableUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CreateScheduleTests {

    private final SchedulesRepository repository;
    private final SchedulesService service;

    @Autowired
    public CreateScheduleTests(SchedulesRepository repository, SchedulesService service) {
        this.repository = repository;
        this.service = service;
    }

    @BeforeEach
    public void setup() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Create a Schedule")
    public void createSchedule() {
        WriteSchedule schedule = new WriteSchedule("schedule-name");

        Schedule result = service.createSchedule(schedule);

        assertEquals("schedule-name", result.getName());

        List<Schedule> schedules = IterableUtils.iterableToList(repository.findAll());
        assertEquals(1, schedules.size());
        assertEquals("schedule-name", schedules.get(0).getName());
    }

    @Test
    @DisplayName("Create schedules with equal names")
    public void createMultipleSchedules() {
        WriteSchedule schedule = new WriteSchedule("schedule-name");

        Schedule first = service.createSchedule(schedule);
        Schedule second = service.createSchedule(schedule);

        assertEquals("schedule-name", first.getName());
        assertEquals("schedule-name", second.getName());
        assert (first.getId() != second.getId());

        List<Schedule> schedules = IterableUtils.iterableToList(repository.findAll());
        assertEquals(2, schedules.size());
    }
}

package com.iscte.mei.ads.schedules.api.services;

import com.iscte.mei.ads.schedules.api.entities.Schedule;
import com.iscte.mei.ads.schedules.api.repositories.LectureRepository;
import com.iscte.mei.ads.schedules.api.repositories.ScheduleRepository;
import com.iscte.mei.ads.schedules.api.utils.IterableUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("Create Schedule Service tests")
public class CreateScheduleTests {

    private final SchedulesService service;

    private final ScheduleRepository scheduleRepository;
    private final LectureRepository lectureRepository;

    @Autowired
    public CreateScheduleTests(SchedulesService service, ScheduleRepository scheduleRepository, LectureRepository lectureRepository) {
        this.service = service;
        this.scheduleRepository = scheduleRepository;
        this.lectureRepository = lectureRepository;
    }

    @BeforeEach
    public void setup() {
        scheduleRepository.deleteAll();
        lectureRepository.deleteAll();
    }

    @Test
    @DisplayName("Create a Schedule")
    public void createScheduleTest() {
        Schedule schedule = new Schedule("schedule-name");

        Schedule result = service.createSchedule(schedule, Collections.emptyList());

        assertEquals("schedule-name", result.getName());

        List<Schedule> schedules = IterableUtils.iterableToList(scheduleRepository.findAll());
        assertEquals(1, schedules.size());
        assertEquals("schedule-name", schedules.get(0).getName());
    }

    @Test
    @DisplayName("Create schedules with equal names")
    public void createMultipleSchedulesTest() {
        Schedule firstSchedule = new Schedule("schedule-name");
        Schedule secondSchedule = new Schedule("schedule-name");

        Schedule first = service.createSchedule(firstSchedule, Collections.emptyList());
        Schedule second = service.createSchedule(secondSchedule, Collections.emptyList());

        assertEquals("schedule-name", first.getName());
        assertEquals("schedule-name", second.getName());
        assert (!first.equals(second));

        List<Schedule> schedules = IterableUtils.iterableToList(scheduleRepository.findAll());
        assertEquals(2, schedules.size());
    }
}

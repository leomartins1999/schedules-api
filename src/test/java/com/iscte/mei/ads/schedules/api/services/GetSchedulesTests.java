package com.iscte.mei.ads.schedules.api.services;

import com.iscte.mei.ads.schedules.api.entities.Schedule;
import com.iscte.mei.ads.schedules.api.repositories.ScheduleRepository;
import com.iscte.mei.ads.schedules.api.utils.IterableUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("Get Schedules Service tests")
public class GetSchedulesTests {

    private final ScheduleRepository repository;
    private final SchedulesService service;

    @Autowired
    public GetSchedulesTests(ScheduleRepository repository, SchedulesService service) {
        this.repository = repository;
        this.service = service;
    }

    @BeforeEach
    public void setup() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Gets no schedules")
    public void getNoSchedulesTest() {
        Iterable<Schedule> result = service.getSchedules();

        List<Schedule> schedules = IterableUtils.iterableToList(result);

        assertEquals(0, schedules.size());
    }

    @Test
    @DisplayName("Gets a schedule")
    public void getScheduleTest() {
        Schedule schedule = repository.save(new Schedule("schedule-name"));

        List<Schedule> result = service.getSchedules();

        assertEquals(1, result.size());
        assertEquals(schedule, result.get(0));
    }

    @Test
    @DisplayName("Gets multiple schedules")
    public void getSchedulesTest() {
        Schedule first = repository.save(new Schedule("schedule-first"));
        Schedule second = repository.save(new Schedule("schedule-second"));

        List<Schedule> result = service.getSchedules();

        assertEquals(2, result.size());
        assertEquals(first, result.get(0));
        assertEquals(second, result.get(1));
    }

    @Test
    @DisplayName("Schedules are ordered alphabetically")
    public void alphabeticallyOrdered() {
        Schedule cSchedule = repository.save(new Schedule("C-schedule"));
        Schedule bSchedule = repository.save(new Schedule("B-schedule"));
        Schedule aSchedule = repository.save(new Schedule("A-schedule"));

        List<Schedule> result = service.getSchedules();

        assertEquals(3, result.size());
        assertEquals(aSchedule, result.get(0));
        assertEquals(bSchedule, result.get(1));
        assertEquals(cSchedule, result.get(2));
    }

}

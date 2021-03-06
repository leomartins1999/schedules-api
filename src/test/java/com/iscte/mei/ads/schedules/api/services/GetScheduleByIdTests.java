package com.iscte.mei.ads.schedules.api.services;

import com.iscte.mei.ads.schedules.api.entities.Schedule;
import com.iscte.mei.ads.schedules.api.repositories.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayName("Get Schedule by Id Service tests")
public class GetScheduleByIdTests {

    private final ScheduleRepository repository;
    private final SchedulesService service;

    @Autowired
    public GetScheduleByIdTests(ScheduleRepository repository, SchedulesService service) {
        this.repository = repository;
        this.service = service;
    }

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Gets a schedule")
    public void getScheduleByIdTest() {
        Schedule s = repository.save(new Schedule("my-schedule"));

        Schedule result = service.getScheduleById(s.getId());

        assertEquals(s, result);
    }

    @Test
    @DisplayName("If the schedule does not exist, noSuchElementException is thrown")
    public void getNonExistingScheduleTest() {
        assertThrows(
                NoSuchElementException.class,
                () -> service.getScheduleById(1)
        );
    }

}

package com.iscte.mei.ads.schedules.api.executors;

import com.iscte.mei.ads.schedules.api.entities.Lecture;
import com.iscte.mei.ads.schedules.api.entities.Schedule;
import com.iscte.mei.ads.schedules.api.entities.ScheduleStatus;
import com.iscte.mei.ads.schedules.api.jobs.ImportLecturesJob;
import com.iscte.mei.ads.schedules.api.repositories.SchedulesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
public class ImportScheduleExecutorTests {

    @Autowired
    private ImportScheduleExecutor executor;

    @Autowired
    private SchedulesRepository repository;

    @MockBean
    private ImportLecturesJob importLecturesJob;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Schedule is imported")
    public void scheduleGoesToDone() throws InterruptedException {
        Schedule s = repository.save(new Schedule("my-schedule"));

        executor.scheduleImport(s, new ArrayList<>());

        Thread.sleep(300);

        Schedule current = repository.findById(s.getId()).get();
        assertEquals(ScheduleStatus.DONE, current.getStatus());
    }

    @Test
    @DisplayName("If an exception occurred, status is updated to ERROR")
    public void scheduleGoesToError() throws InterruptedException {
        Schedule s = repository.save(new Schedule("my-schedule"));
        Iterable<Lecture> l = new ArrayList<>();

        doThrow(RuntimeException.class).when(importLecturesJob).execute(s.getId(), l);

        executor.scheduleImport(s, l);

        Thread.sleep(300);

        Schedule current = repository.findById(s.getId()).get();
        assertEquals(ScheduleStatus.ERROR, current.getStatus());
    }
}

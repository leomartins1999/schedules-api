package com.iscte.mei.ads.schedules.api.listeners;

import com.iscte.mei.ads.schedules.api.entities.Schedule;
import com.iscte.mei.ads.schedules.api.entities.ScheduleStatus;
import com.iscte.mei.ads.schedules.api.executors.ScheduleJobsExecutor;
import com.iscte.mei.ads.schedules.api.repositories.SchedulesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class PendingSchedulesEnqueuerTests {

    @Autowired
    private PendingSchedulesEnqueuer enqueuer;

    @Autowired
    private SchedulesRepository repository;

    @MockBean
    private ScheduleJobsExecutor executor;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("No schedules to enqueue")
    void enqueuesNoSchedules() {
        enqueuer.onApplicationEvent(null);

        verify(executor, never()).scheduleCalculation(any());
    }

    @Test
    @DisplayName("Enqueues calculating schedules")
    void enqueuesCalculatingSchedules() {
        Schedule calculatingSchedule = new Schedule("my-schedule");
        calculatingSchedule.setStatus(ScheduleStatus.CALCULATING);

        Schedule anotherSchedule = new Schedule("another-schedule");
        anotherSchedule.setStatus(ScheduleStatus.CALCULATING);

        Schedule errorSchedule = new Schedule("my-schedule");
        errorSchedule.setStatus(ScheduleStatus.ERROR);

        repository.save(calculatingSchedule);
        repository.save(anotherSchedule);
        repository.save(errorSchedule);

        enqueuer.onApplicationEvent(null);

        verify(executor).scheduleCalculation(calculatingSchedule);
        verify(executor).scheduleCalculation(anotherSchedule);
        verify(executor, never()).scheduleCalculation(errorSchedule);
    }
}

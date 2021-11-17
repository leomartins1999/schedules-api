package com.iscte.mei.ads.schedules.api.executors;

import com.iscte.mei.ads.schedules.api.entities.Lecture;
import com.iscte.mei.ads.schedules.api.entities.Schedule;
import com.iscte.mei.ads.schedules.api.entities.ScheduleStatus;
import com.iscte.mei.ads.schedules.api.jobs.CalculateScoresJob;
import com.iscte.mei.ads.schedules.api.jobs.ImportLecturesJob;
import com.iscte.mei.ads.schedules.api.repositories.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.stubbing.answers.AnswersWithDelay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ScheduleJobsExecutorTests {

    @Autowired
    private ScheduleJobsExecutor executor;

    @Autowired
    private ScheduleRepository repository;

    @MockBean
    private ImportLecturesJob importLecturesJob;

    @MockBean
    private CalculateScoresJob calculateScoresJob;

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
    @DisplayName("If an exception occurred while importing, status is updated to ERROR")
    public void errorWhileImporting() throws InterruptedException {
        Schedule s = repository.save(new Schedule("my-schedule"));
        Iterable<Lecture> l = new ArrayList<>();

        doThrow(RuntimeException.class).when(importLecturesJob).execute(s.getId(), l);

        executor.scheduleImport(s, l);

        Thread.sleep(300);

        Schedule current = repository.findById(s.getId()).get();
        assertEquals(ScheduleStatus.ERROR, current.getStatus());
    }

    @Test
    @DisplayName("If an exception occurred while calculating, status is updated to ERROR")
    public void errorWhileCalculating() throws InterruptedException {
        Schedule s = repository.save(new Schedule("my-schedule"));
        Iterable<Lecture> l = new ArrayList<>();

        doThrow(RuntimeException.class).when(calculateScoresJob).execute(s.getId());

        executor.scheduleImport(s, l);

        Thread.sleep(300);

        Schedule current = repository.findById(s.getId()).get();
        assertEquals(ScheduleStatus.ERROR, current.getStatus());
    }

    @Test
    @DisplayName("While a schedule is being imported, it is in IMPORTING status")
    void importSchedule() throws InterruptedException {
        Schedule s = repository.save(new Schedule("my-schedule"));
        Iterable<Lecture> l = new ArrayList<>();

        doAnswer(new AnswersWithDelay(10000, null))
                .when(importLecturesJob)
                .execute(s.getId(), l);

        executor.scheduleImport(s, l);

        Thread.sleep(300);

        Schedule current = repository.findById(s.getId()).get();
        assertEquals(ScheduleStatus.IMPORTING, current.getStatus());
    }

    @Test
    @DisplayName("While a schedule is being calculated, it is in CALCULATING status")
    void calculateSchedule() throws InterruptedException {
        Schedule s = repository.save(new Schedule("my-schedule"));
        Iterable<Lecture> l = new ArrayList<>();

        doAnswer(new AnswersWithDelay(10000, null))
                .when(calculateScoresJob)
                .execute(s.getId());

        executor.scheduleImport(s, l);

        Thread.sleep(300);

        Schedule current = repository.findById(s.getId()).get();
        assertEquals(ScheduleStatus.CALCULATING, current.getStatus());
    }

    @Test
    @DisplayName("If scheduling an import, both jobs are invoked")
    void bothJobsAreInvoked() throws InterruptedException {
        Schedule s = repository.save(new Schedule("my-schedule"));
        Iterable<Lecture> l = new ArrayList<>();

        executor.scheduleImport(s, l);

        Thread.sleep(300);

        verify(importLecturesJob).execute(s.getId(), l);
        verify(calculateScoresJob).execute(s.getId());
    }

    @Test
    @DisplayName("If scheduling a calculation, only calculation job is invoked")
    void onlyCalculateJobIsInvoked() throws InterruptedException {
        Schedule s = repository.save(new Schedule("my-schedule"));
        Iterable<Lecture> l = new ArrayList<>();

        executor.scheduleCalculation(s);

        Thread.sleep(300);

        verify(calculateScoresJob).execute(s.getId());
        verify(importLecturesJob, never()).execute(s.getId(), l);
    }
}

package com.iscte.mei.ads.schedules.api.services;

import com.iscte.mei.ads.schedules.api.entities.Lecture;
import com.iscte.mei.ads.schedules.api.entities.Schedule;
import com.iscte.mei.ads.schedules.api.executors.ScheduleJobsExecutor;
import com.iscte.mei.ads.schedules.api.repositories.SchedulesRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SchedulesService {

    private final SchedulesRepository schedulesRepository;
    private final ScheduleJobsExecutor executor;

    public SchedulesService(SchedulesRepository schedulesRepository, ScheduleJobsExecutor executor) {
        this.schedulesRepository = schedulesRepository;
        this.executor = executor;
    }

    public Schedule createSchedule(Schedule schedule, Iterable<Lecture> lectures) {
        Schedule s = schedulesRepository.save(schedule);

        executor.scheduleImport(s, lectures);

        return s;
    }

    public Iterable<Schedule> getSchedules() {
        return schedulesRepository.findAll();
    }

    public Schedule getScheduleById(long scheduleId) {
        return schedulesRepository.findById(scheduleId).get();
    }

    public Iterable<String> getDatesForSchedule(long scheduleId) {
        if (schedulesRepository.findById(scheduleId).isEmpty()) throw new NoSuchElementException();

        return schedulesRepository.getDatesForSchedule(scheduleId);
    }
}

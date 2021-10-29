package com.iscte.mei.ads.schedules.api.services;

import com.iscte.mei.ads.schedules.api.entities.Lecture;
import com.iscte.mei.ads.schedules.api.entities.Schedule;
import com.iscte.mei.ads.schedules.api.executors.ImportScheduleExecutor;
import com.iscte.mei.ads.schedules.api.repositories.SchedulesRepository;
import org.springframework.stereotype.Service;

@Service
public class SchedulesService {

    private final SchedulesRepository schedulesRepository;
    private final ImportScheduleExecutor executor;

    public SchedulesService(SchedulesRepository schedulesRepository, ImportScheduleExecutor executor) {
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
}

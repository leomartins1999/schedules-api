package com.iscte.mei.ads.schedules.api.services;

import com.iscte.mei.ads.schedules.api.entities.Lecture;
import com.iscte.mei.ads.schedules.api.entities.Schedule;
import com.iscte.mei.ads.schedules.api.executors.ScheduleJobsExecutor;
import com.iscte.mei.ads.schedules.api.repositories.LectureRepository;
import com.iscte.mei.ads.schedules.api.repositories.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SchedulesService {

    private final ScheduleRepository scheduleRepository;
    private final LectureRepository lectureRepository;
    private final ScheduleJobsExecutor executor;

    public SchedulesService(ScheduleRepository scheduleRepository, LectureRepository lectureRepository, ScheduleJobsExecutor executor) {
        this.scheduleRepository = scheduleRepository;
        this.lectureRepository = lectureRepository;
        this.executor = executor;
    }

    public Schedule createSchedule(Schedule schedule, Iterable<Lecture> lectures) {
        Schedule s = scheduleRepository.save(schedule);

        executor.scheduleImport(s, lectures);

        return s;
    }

    public Iterable<Schedule> getSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule getScheduleById(long scheduleId) {
        return scheduleRepository.findById(scheduleId).get();
    }

    public Iterable<String> getDatesForSchedule(long scheduleId) {
        if (scheduleRepository.findById(scheduleId).isEmpty()) throw new NoSuchElementException();

        return lectureRepository.getDatesForSchedule(scheduleId);
    }
}

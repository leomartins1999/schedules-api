package com.iscte.mei.ads.schedules.api.services;

import com.iscte.mei.ads.schedules.api.entities.Lecture;
import com.iscte.mei.ads.schedules.api.entities.Schedule;
import com.iscte.mei.ads.schedules.api.executors.ScheduleJobsExecutor;
import com.iscte.mei.ads.schedules.api.repositories.LectureRepository;
import com.iscte.mei.ads.schedules.api.repositories.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

    public Iterable<String> getClassesForSchedule(long scheduleId) {
        if (scheduleRepository.findById(scheduleId).isEmpty()) throw new NoSuchElementException();

        List<String> classes = lectureRepository.getClassesForSchedule(scheduleId);
        return decomposeClasses(classes);
    }

    public List<Lecture> getLecturesForSchedule(long scheduleId, String klass, String startDate, String endDate) {
        if (scheduleRepository.findById(scheduleId).isEmpty()) throw new NoSuchElementException();

        return lectureRepository.getLecturesForSchedule(
                scheduleId,
                klass,
                startDate,
                endDate
        );
    }

    /**
     * Some classes are composed ie: 'ABC, DEF'
     * This method decomposes these classes
     */
    private List<String> decomposeClasses(List<String> classes) {
        return classes.stream()
                .map(klass -> klass.split(","))
                .flatMap(Arrays::stream)
                .distinct()
                .map(String::trim)
                .collect(Collectors.toList());
    }
}

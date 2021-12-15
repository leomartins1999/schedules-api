package com.iscte.mei.ads.schedules.api.services;

import com.iscte.mei.ads.schedules.api.entities.Lecture;
import com.iscte.mei.ads.schedules.api.entities.Schedule;
import com.iscte.mei.ads.schedules.api.entities.ScheduleStatus;
import com.iscte.mei.ads.schedules.api.entities.Score;
import com.iscte.mei.ads.schedules.api.executors.ScheduleJobsExecutor;
import com.iscte.mei.ads.schedules.api.repositories.LectureRepository;
import com.iscte.mei.ads.schedules.api.repositories.ScheduleRepository;
import com.iscte.mei.ads.schedules.api.repositories.ScoreRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SchedulesService {

    private final ScheduleRepository scheduleRepository;
    private final LectureRepository lectureRepository;
    private final ScoreRepository scoreRepository;
    private final ScheduleJobsExecutor executor;

    public SchedulesService(
            ScheduleRepository scheduleRepository,
            LectureRepository lectureRepository,
            ScoreRepository scoreRepository,
            ScheduleJobsExecutor executor
    ) {
        this.scheduleRepository = scheduleRepository;
        this.lectureRepository = lectureRepository;
        this.scoreRepository = scoreRepository;
        this.executor = executor;
    }

    public Schedule createSchedule(Schedule schedule, Iterable<Lecture> lectures) {
        Schedule s = scheduleRepository.save(schedule);

        executor.scheduleImport(s, lectures);

        return s;
    }

    public List<Schedule> getSchedules() {
        return scheduleRepository.getSchedulesOrderedByName();
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

    public Score getScoresForSchedule(long scheduleId) {
        Optional<Schedule> optional = scheduleRepository.findById(scheduleId);
        if (optional.isEmpty()) throw new NoSuchElementException();

        Schedule s = optional.get();
        if (s.getStatus() != ScheduleStatus.DONE) throw new IllegalStateException();

        return scoreRepository.getByScheduleId(scheduleId);
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

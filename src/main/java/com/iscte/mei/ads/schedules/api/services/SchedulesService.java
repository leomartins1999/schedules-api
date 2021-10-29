package com.iscte.mei.ads.schedules.api.services;

import com.iscte.mei.ads.schedules.api.entities.Lecture;
import com.iscte.mei.ads.schedules.api.entities.Schedule;
import com.iscte.mei.ads.schedules.api.repositories.LecturesRepository;
import com.iscte.mei.ads.schedules.api.repositories.SchedulesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SchedulesService {

    private final SchedulesRepository schedulesRepository;
    private final LecturesRepository lecturesRepository;

    public SchedulesService(SchedulesRepository schedulesRepository, LecturesRepository lecturesRepository) {
        this.schedulesRepository = schedulesRepository;
        this.lecturesRepository = lecturesRepository;
    }

    public Schedule createSchedule(Schedule schedule, Iterable<Lecture> lectures) {
        Schedule s = schedulesRepository.save(schedule);

        saveLectures(s.getId(), lectures);

        return s;
    }

    public Iterable<Schedule> getSchedules() {
        return schedulesRepository.findAll();
    }

    private void saveLectures(long scheduleId, Iterable<Lecture> lectures) {
        ArrayList<Lecture> entities = new ArrayList<>();

        for (Lecture l : lectures) entities.add(l.withScheduleId(scheduleId));

        lecturesRepository.saveAll(entities);
    }
}

package com.iscte.mei.ads.schedules.api.controllers;

import com.iscte.mei.ads.schedules.api.deserializers.LectureListDeserializer;
import com.iscte.mei.ads.schedules.api.entities.Lecture;
import com.iscte.mei.ads.schedules.api.entities.Schedule;
import com.iscte.mei.ads.schedules.api.models.WriteLecture;
import com.iscte.mei.ads.schedules.api.models.WriteSchedule;
import com.iscte.mei.ads.schedules.api.services.SchedulesService;
import com.iscte.mei.ads.schedules.api.utils.IterableUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(Paths.SCHEDULES_MAPPING)
public class SchedulesController {

    private final SchedulesService service;

    private final LectureListDeserializer deserializer;

    public SchedulesController(SchedulesService service, LectureListDeserializer deserializer) {
        this.service = service;
        this.deserializer = deserializer;
    }

    @GetMapping
    public Iterable<Schedule> getSchedules() {
        return service.getSchedules();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Schedule createSchedule(@RequestBody @Valid WriteSchedule model) throws IOException {
        Schedule schedule = model.toSchedule();

        Iterable<WriteLecture> lectureModels = deserializer.deserialize(model.getContent(), model.getFormat());

        Iterable<Lecture> lectures = IterableUtils.map(lectureModels, WriteLecture::toLecture);

        return service.createSchedule(schedule, lectures);
    }

    @GetMapping(Paths.SCHEDULE_BY_ID_MAPPING)
    public Schedule getScheduleById(@PathVariable long id) {
        return service.getScheduleById(id);
    }

    @GetMapping(Paths.CLASSES_FOR_SCHEDULE_MAPPING)
    public Schedule getClassesForSchedule(@PathVariable String id) {
        throw new IllegalStateException();
    }

    @GetMapping(Paths.DATES_FOR_SCHEDULE_MAPPING)
    public Schedule getDatesForSchedule(@PathVariable String id) {
        throw new IllegalStateException();
    }

    @GetMapping(Paths.LECTURES_FOR_SCHEDULE_MAPPING)
    public Schedule getLecturesForSchedule(@PathVariable String id) {
        throw new IllegalStateException();
    }

    @GetMapping(Paths.SCORES_FOR_SCHEDULE_MAPPING)
    public Schedule getScoresForSchedule(@PathVariable String id) {
        throw new IllegalStateException();
    }

}

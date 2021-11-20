package com.iscte.mei.ads.schedules.api.controllers;

import com.iscte.mei.ads.schedules.api.deserializers.LectureListDeserializer;
import com.iscte.mei.ads.schedules.api.entities.Lecture;
import com.iscte.mei.ads.schedules.api.entities.Schedule;
import com.iscte.mei.ads.schedules.api.models.WriteLecture;
import com.iscte.mei.ads.schedules.api.models.WriteSchedule;
import com.iscte.mei.ads.schedules.api.services.SchedulesService;
import com.iscte.mei.ads.schedules.api.utils.IterableUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

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
    public Iterable<String> getClassesForSchedule(@PathVariable long id) {
        return service.getClassesForSchedule(id);
    }

    @GetMapping(Paths.DATES_FOR_SCHEDULE_MAPPING)
    public Iterable<String> getDatesForSchedule(@PathVariable long id) {
        return service.getDatesForSchedule(id);
    }

    @GetMapping(Paths.LECTURES_FOR_SCHEDULE_MAPPING)
    public List<Lecture> getLecturesForSchedule(
            @PathVariable long id,
            @RequestParam String klass,
            @RequestParam(name = "start_date") String startDate,
            @RequestParam(name = "end_date") String endDate
    ) {
        if (isQueryParameterInvalid(klass) || isQueryParameterInvalid(startDate) || isQueryParameterInvalid(endDate))
            throw new IllegalArgumentException();

        return service.getLecturesForSchedule(id, klass, startDate, endDate);
    }

    @GetMapping(Paths.SCORES_FOR_SCHEDULE_MAPPING)
    public Schedule getScoresForSchedule(@PathVariable String id) {
        throw new IllegalStateException();
    }

    private boolean isQueryParameterInvalid(String value) {
        return value == null || value.isEmpty() || value.isBlank();
    }

}

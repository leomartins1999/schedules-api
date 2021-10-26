package com.iscte.mei.ads.schedules.api.controllers;

import com.iscte.mei.ads.schedules.api.entities.Schedule;
import com.iscte.mei.ads.schedules.api.models.WriteSchedule;
import com.iscte.mei.ads.schedules.api.services.SchedulesService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(Paths.SCHEDULES_MAPPING)
public class SchedulesController {

    private final SchedulesService service;

    public SchedulesController(SchedulesService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<Schedule> getSchedules() {
        throw new IllegalStateException();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Schedule createSchedule(@RequestBody @Valid WriteSchedule model) {
        return service.createSchedule(model);
    }

    @GetMapping(Paths.SCHEDULE_BY_ID_MAPPING)
    public Schedule getScheduleById(@PathVariable String id) {
        throw new IllegalStateException();
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

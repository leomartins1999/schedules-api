package com.iscte.mei.ads.schedules.api.controllers;

import com.iscte.mei.ads.schedules.api.entities.Schedule;
import com.iscte.mei.ads.schedules.api.services.SchedulesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(Paths.SCHEDULES_MAPPING)
public class SchedulesController {

    private final SchedulesService service;

    public SchedulesController(SchedulesService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<Schedule> getSchedules() {
        throw new IllegalStateException();
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

    @PostMapping
    public Schedule createSchedule() {
        throw new IllegalStateException();
    }

}

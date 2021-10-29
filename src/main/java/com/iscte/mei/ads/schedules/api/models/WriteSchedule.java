package com.iscte.mei.ads.schedules.api.models;

import com.iscte.mei.ads.schedules.api.entities.Schedule;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class WriteSchedule {

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @NotNull
    private ScheduleFormat format;

    @NotNull
    @NotEmpty
    @NotBlank
    private String content;

    public WriteSchedule(String name, ScheduleFormat format, String content) {
        this.name = name;
        this.format = format;
        this.content = content;
    }

    public WriteSchedule() {
    }

    public String getName() {
        return name;
    }

    public ScheduleFormat getFormat() {
        return format;
    }

    public String getContent() {
        return content;
    }

    public Schedule toSchedule() {
        return new Schedule(name);
    }
}

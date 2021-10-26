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

    public WriteSchedule(String name) {
        this.name = name;
    }

    public WriteSchedule() {
    }

    public Schedule toSchedule() {
        return new Schedule(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

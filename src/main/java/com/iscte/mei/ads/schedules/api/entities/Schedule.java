package com.iscte.mei.ads.schedules.api.entities;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class Schedule {

    @Id
    private long id;
    private String name;
    private ScheduleStatus status;

    public Schedule(String name) {
        this.id = 0;
        this.name = name;
        this.status = ScheduleStatus.CREATED;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return id == schedule.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

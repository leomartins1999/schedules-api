package com.iscte.mei.ads.schedules.api.entities;

import org.springframework.data.annotation.Id;

public class Schedule {

    @Id
    private long id;
    private String name;

    public Schedule(String name) {
        this.id = 0;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

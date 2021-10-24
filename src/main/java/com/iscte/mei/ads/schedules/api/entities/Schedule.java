package com.iscte.mei.ads.schedules.api.entities;

import org.springframework.data.annotation.Id;

public class Schedule {

    @Id
    private final long id;
    private final String name;

    public Schedule(long id, String name) {
        this.id = id;
        this.name = name;
    }
}

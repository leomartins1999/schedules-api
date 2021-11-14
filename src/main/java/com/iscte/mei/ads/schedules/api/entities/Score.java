package com.iscte.mei.ads.schedules.api.entities;

import org.springframework.data.annotation.Id;

public class Score {

    @Id
    private long id;
    private long scheduleId;

    private float pctOverflowingLectures;

    public Score(long scheduleId, float pctOverflowingLectures) {
        this.scheduleId = scheduleId;
        this.pctOverflowingLectures = pctOverflowingLectures;
    }

    public long getScheduleId() {
        return scheduleId;
    }

    public float getPctOverflowingLectures() {
        return pctOverflowingLectures;
    }
}

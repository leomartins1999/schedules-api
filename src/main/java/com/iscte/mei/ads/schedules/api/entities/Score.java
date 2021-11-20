package com.iscte.mei.ads.schedules.api.entities;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class Score {

    @Id
    private long id;
    private long scheduleId;

    private float pctOverflowingLectures;
    private long nrUsedRooms;

    public Score(long scheduleId, float pctOverflowingLectures, long nrUsedRooms) {
        this.scheduleId = scheduleId;
        this.pctOverflowingLectures = pctOverflowingLectures;
        this.nrUsedRooms = nrUsedRooms;
    }

    public long getScheduleId() {
        return scheduleId;
    }

    public float getPctOverflowingLectures() {
        return pctOverflowingLectures;
    }

    public long getNrUsedRooms() {
        return nrUsedRooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return scheduleId == score.scheduleId && Float.compare(score.pctOverflowingLectures, pctOverflowingLectures) == 0 && nrUsedRooms == score.nrUsedRooms;
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId, pctOverflowingLectures, nrUsedRooms);
    }
}

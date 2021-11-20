package com.iscte.mei.ads.schedules.api.entities;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class Score {

    @Id
    private long id;
    private final long scheduleId;

    private final float pctOverflowingLectures;
    private final long nrUsedRooms;
    private final float pctOverqualifiedRoomsForLectures;

    public Score(
            long scheduleId,
            float pctOverflowingLectures,
            long nrUsedRooms,
            float pctOverqualifiedRoomsForLectures
    ) {
        this.scheduleId = scheduleId;
        this.pctOverflowingLectures = pctOverflowingLectures;
        this.nrUsedRooms = nrUsedRooms;
        this.pctOverqualifiedRoomsForLectures = pctOverqualifiedRoomsForLectures;
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

    public float getPctOverqualifiedRoomsForLectures() {
        return pctOverqualifiedRoomsForLectures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return scheduleId == score.scheduleId && Float.compare(score.pctOverflowingLectures, pctOverflowingLectures) == 0 && nrUsedRooms == score.nrUsedRooms && Float.compare(score.pctOverqualifiedRoomsForLectures, pctOverqualifiedRoomsForLectures) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId, pctOverflowingLectures, nrUsedRooms, pctOverqualifiedRoomsForLectures);
    }
}

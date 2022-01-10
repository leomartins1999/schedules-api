package com.iscte.mei.ads.schedules.api.models.aggregatescores;

import org.springframework.data.relational.core.mapping.Column;

public abstract class AggregateScores {

    @Column("nrUsedRooms")
    private int nrUsedRooms;

    @Column("pctOverqualifiedRoomsForLectures")
    private float pctOverqualifiedRoomsForLectures;

    @Column("pctOverflowingLectures")
    private float pctOverflowingLectures;

    @Column("nrLectures")
    private int nrLectures;

    abstract public String getKey();

    public int getNrUsedRooms() {
        return nrUsedRooms;
    }

    public float getPctOverqualifiedRoomsForLectures() {
        return pctOverqualifiedRoomsForLectures;
    }

    public float getPctOverflowingLectures() {
        return pctOverflowingLectures;
    }

    public int getNrLectures() {
        return nrLectures;
    }
}

package com.iscte.mei.ads.schedules.api.models;

import com.iscte.mei.ads.schedules.api.models.aggregatescores.AggregateScores;

import java.util.Map;

public class SchedulePivotedScores {

    private final long scheduleId;
    private final Map<String, AggregateScores[]> scores;

    public SchedulePivotedScores(long scheduleId, Map<String, AggregateScores[]> scores) {
        this.scheduleId = scheduleId;
        this.scores = scores;
    }

    public long getScheduleId() {
        return scheduleId;
    }

    public Map<String, AggregateScores[]> getScores() {
        return scores;
    }
}

package com.iscte.mei.ads.schedules.api.models.aggregatescores;

import org.springframework.data.relational.core.mapping.Column;

public class AggregateScoresByWeek extends AggregateScores {

    @Column("week")
    private String week;

    @Override
    public String getKey() {
        return week;
    }

}

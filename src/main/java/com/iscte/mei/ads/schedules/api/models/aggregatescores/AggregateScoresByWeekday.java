package com.iscte.mei.ads.schedules.api.models.aggregatescores;

import org.springframework.data.relational.core.mapping.Column;

public class AggregateScoresByWeekday extends AggregateScores {

    private static final String[] WEEKDAYS = new String[]{
            "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    };

    @Column("weekIdx")
    private int weekIdx;

    @Override
    public String getKey() {
        return WEEKDAYS[weekIdx];
    }
}

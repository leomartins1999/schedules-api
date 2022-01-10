package com.iscte.mei.ads.schedules.api.interactor;

import com.iscte.mei.ads.schedules.api.models.DatePeriod;
import com.iscte.mei.ads.schedules.api.models.SchedulePivotedScores;
import com.iscte.mei.ads.schedules.api.models.aggregatescores.AggregateScores;
import com.iscte.mei.ads.schedules.api.repositories.LectureRepository;
import com.iscte.mei.ads.schedules.api.repositories.ScheduleRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
public class GetPivotedScoresInteractor {

    private final ScheduleRepository scheduleRepository;
    private final LectureRepository lectureRepository;

    public GetPivotedScoresInteractor(ScheduleRepository scheduleRepository, LectureRepository lectureRepository) {
        this.scheduleRepository = scheduleRepository;
        this.lectureRepository = lectureRepository;
    }

    public SchedulePivotedScores execute(long scheduleId) {
        if (scheduleRepository.findById(scheduleId).isEmpty()) {
            throw new NoSuchElementException();
        }

        Map<String, AggregateScores[]> scores = getPivotedScores(scheduleId);

        return new SchedulePivotedScores(scheduleId, scores);
    }

    private Map<String, AggregateScores[]> getPivotedScores(long scheduleId) {
        HashMap<String, AggregateScores[]> map = new HashMap<>();

        map.put("weekday", getScoresByWeekDay(scheduleId));
        map.put("week", getScoresByWeek(scheduleId));

        return map;
    }

    private AggregateScores[] getScoresByWeekDay(long scheduleId) {
        return lectureRepository.getScoresByWeekday(scheduleId);
    }

    private AggregateScores[] getScoresByWeek(long scheduleId) {
        DatePeriod period = lectureRepository.getDatePeriod(scheduleId);

        return getWeekScores(scheduleId, period.getWeeks());
    }

    private AggregateScores[] getWeekScores(long scheduleId, List<DatePeriod> weeks) {
        return weeks.stream()
                .map(p -> lectureRepository.getScoresByWeek(scheduleId, p.toString(), p.getStartDate(), p.getEndDate()))
                .toArray(AggregateScores[]::new);
    }

}

package com.iscte.mei.ads.schedules.api.jobs;

import com.iscte.mei.ads.schedules.api.entities.Score;
import com.iscte.mei.ads.schedules.api.repositories.LecturesRepository;
import com.iscte.mei.ads.schedules.api.repositories.ScoreRepository;
import org.springframework.stereotype.Component;

@Component
public class CalculateScoresJob {

    private final ScoreRepository scoreRepository;
    private final LecturesRepository lecturesRepository;

    public CalculateScoresJob(ScoreRepository scoreRepository, LecturesRepository lecturesRepository) {
        this.scoreRepository = scoreRepository;
        this.lecturesRepository = lecturesRepository;
    }

    public void execute(long scheduleId) {
        Score score = buildScore(scheduleId);

        scoreRepository.save(score);
    }

    private Score buildScore(long scheduleId) {
        return new Score(
                scheduleId,
                lecturesRepository.getPctOverflowingLectures(scheduleId)
        );
    }
}

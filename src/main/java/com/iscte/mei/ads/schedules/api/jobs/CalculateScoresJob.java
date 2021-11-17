package com.iscte.mei.ads.schedules.api.jobs;

import com.iscte.mei.ads.schedules.api.entities.Score;
import com.iscte.mei.ads.schedules.api.repositories.LectureRepository;
import com.iscte.mei.ads.schedules.api.repositories.ScoreRepository;
import org.springframework.stereotype.Component;

@Component
public class CalculateScoresJob {

    private final ScoreRepository scoreRepository;
    private final LectureRepository lectureRepository;

    public CalculateScoresJob(ScoreRepository scoreRepository, LectureRepository lectureRepository) {
        this.scoreRepository = scoreRepository;
        this.lectureRepository = lectureRepository;
    }

    public void execute(long scheduleId) {
        Score score = buildScore(scheduleId);

        scoreRepository.save(score);
    }

    private Score buildScore(long scheduleId) {
        return new Score(
                scheduleId,
                lectureRepository.getPctOverflowingLectures(scheduleId)
        );
    }
}

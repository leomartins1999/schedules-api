package com.iscte.mei.ads.schedules.api.jobs;

import com.iscte.mei.ads.schedules.api.entities.Lecture;
import com.iscte.mei.ads.schedules.api.entities.Score;
import com.iscte.mei.ads.schedules.api.repositories.LectureRepository;
import com.iscte.mei.ads.schedules.api.repositories.ScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("Percentage of Overqualified Rooms for Lectures tests")
public class PercentageOfOverqualifiedRoomsTests {

    private static final long SCHEDULE_ID = 1L;

    @Autowired
    private CalculateScoresJob job;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @BeforeEach
    void setUp() {
        scoreRepository.deleteAll();
        lectureRepository.deleteAll();
    }

    @Test
    @DisplayName("If no rooms are overqualified, the percentage is 0")
    void noRoomsOverqualified() {
        job.execute(SCHEDULE_ID);

        Score s = getScore();

        assertEquals(SCHEDULE_ID, s.getScheduleId());
        assertEquals(0, s.getPctOverqualifiedRoomsForLectures());
    }

    @Test
    @DisplayName("If half the rooms are overqualified, the percentage is 50")
    void halfRoomsOverqualified() {
        saveTestLecture(false);
        saveTestLecture(true);

        job.execute(SCHEDULE_ID);

        Score s = getScore();

        assertEquals(SCHEDULE_ID, s.getScheduleId());
        assertEquals(0.5, s.getPctOverqualifiedRoomsForLectures());
    }

    @Test
    @DisplayName("If all the rooms are overqualified, the percentage is 100")
    void allRoomsOverqualified() {
        saveTestLecture(true);
        saveTestLecture(true);

        job.execute(SCHEDULE_ID);

        Score s = getScore();

        assertEquals(SCHEDULE_ID, s.getScheduleId());
        assertEquals(1, s.getPctOverqualifiedRoomsForLectures());
    }

    private Score getScore() {
        return scoreRepository
                .findAll()
                .iterator()
                .next();
    }

    private void saveTestLecture(boolean isOverqualified) {
        Lecture l = new Lecture(
                "",
                "",
                "",
                "",
                "",
                "2021-03-03",
                "11:00:00",
                "12:30:00",
                10,
                20,
                "",
                "",
                isOverqualified,
                false
        ).withScheduleId(SCHEDULE_ID);

        lectureRepository.save(l);
    }

}

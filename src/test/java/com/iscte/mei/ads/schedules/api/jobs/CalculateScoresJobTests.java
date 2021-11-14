package com.iscte.mei.ads.schedules.api.jobs;

import com.iscte.mei.ads.schedules.api.entities.Lecture;
import com.iscte.mei.ads.schedules.api.entities.Score;
import com.iscte.mei.ads.schedules.api.repositories.LecturesRepository;
import com.iscte.mei.ads.schedules.api.repositories.ScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CalculateScoresJobTests {

    private static final long SCHEDULE_ID = 1L;

    @Autowired
    private CalculateScoresJob job;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private LecturesRepository lecturesRepository;

    @BeforeEach
    void setUp() {
        scoreRepository.deleteAll();
        lecturesRepository.deleteAll();
    }

    @Nested
    @DisplayName("Percentage of overflowing lectures")
    class PctOverflowingClassesTest {

        @Test
        @DisplayName("No lectures are overflowing")
        void noOverflowingLectures() {
            int roomCapacity = 50;

            saveTestLecture(5, roomCapacity);
            saveTestLecture(15, roomCapacity);

            job.execute(SCHEDULE_ID);

            Score s = getScore();

            assertEquals(SCHEDULE_ID, s.getScheduleId());
            assertEquals(0F, s.getPctOverflowingLectures());
        }

        @Test
        @DisplayName("50% of the lectures are overflowing")
        void someOverflowingLectures() {
            int roomCapacity = 10;

            saveTestLecture(5, roomCapacity);
            saveTestLecture(15, roomCapacity);

            job.execute(SCHEDULE_ID);

            Score s = getScore();

            assertEquals(SCHEDULE_ID, s.getScheduleId());
            assertEquals(0.5, s.getPctOverflowingLectures());
        }

        @Test
        @DisplayName("All lectures are overflowing")
        void allOverflowingLectures() {
            int roomCapacity = 2;

            saveTestLecture(5, roomCapacity);
            saveTestLecture(15, roomCapacity);

            job.execute(SCHEDULE_ID);

            Score s = getScore();

            assertEquals(SCHEDULE_ID, s.getScheduleId());
            assertEquals(1F, s.getPctOverflowingLectures());
        }
    }

    private Score getScore() {
        return scoreRepository
                .findAll()
                .iterator()
                .next();
    }

    private void saveTestLecture(int signedUp, int maxForRoom) {
        Lecture l = new Lecture(
                "",
                "",
                "",
                "",
                "",
                "2021-03-03",
                "11:00:00",
                "12:30:00",
                signedUp,
                maxForRoom,
                "",
                "",
                false,
                false
        ).withScheduleId(CalculateScoresJobTests.SCHEDULE_ID);

        lecturesRepository.save(l);
    }

}
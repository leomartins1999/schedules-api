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
@DisplayName("Number of Used Rooms tests")
public class NumberOfUsedRoomsTest {

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
    @DisplayName("One room is used")
    void singleRoomUsed() {
        saveTestLecture("room-1");
        saveTestLecture("room-1");
        saveTestLecture("room-1");

        job.execute(SCHEDULE_ID);

        Score s = getScore();

        assertEquals(SCHEDULE_ID, s.getScheduleId());
        assertEquals(1, s.getNrUsedRooms());
    }

    @Test
    @DisplayName("Multiple rooms are used")
    void multipleRoomsUsed() {
        saveTestLecture("room-1");
        saveTestLecture("room-2");
        saveTestLecture("room-2");

        job.execute(SCHEDULE_ID);

        Score s = getScore();

        assertEquals(SCHEDULE_ID, s.getScheduleId());
        assertEquals(2, s.getNrUsedRooms());
    }

    @Test
    @DisplayName("If there are no lectures, no rooms are used")
    void noRoomsUsed() {
        job.execute(SCHEDULE_ID);

        Score s = getScore();

        assertEquals(SCHEDULE_ID, s.getScheduleId());
        assertEquals(0, s.getNrUsedRooms());
    }

    private Score getScore() {
        return scoreRepository
                .findAll()
                .iterator()
                .next();
    }

    private void saveTestLecture(String room) {
        Lecture l = new Lecture(
                "",
                "",
                "",
                "",
                room,
                "2021-03-03",
                "11:00:00",
                "12:30:00",
                0,
                0,
                "",
                "",
                false,
                false
        ).withScheduleId(SCHEDULE_ID);

        lectureRepository.save(l);
    }

}

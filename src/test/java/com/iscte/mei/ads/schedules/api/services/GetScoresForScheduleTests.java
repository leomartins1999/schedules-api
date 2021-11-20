package com.iscte.mei.ads.schedules.api.services;

import com.iscte.mei.ads.schedules.api.entities.Schedule;
import com.iscte.mei.ads.schedules.api.entities.ScheduleStatus;
import com.iscte.mei.ads.schedules.api.entities.Score;
import com.iscte.mei.ads.schedules.api.repositories.ScheduleRepository;
import com.iscte.mei.ads.schedules.api.repositories.ScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class GetScoresForScheduleTests {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private SchedulesService service;

    @BeforeEach
    void setUp() {
        scheduleRepository.deleteAll();
        scoreRepository.deleteAll();
    }

    @Test
    @DisplayName("Gets the scores for a given schedule")
    void getScores() {
        Schedule s = new Schedule("calculating-schedule");
        s.setStatus(ScheduleStatus.DONE);

        scheduleRepository.save(s);

        Score score = scoreRepository.save(new Score(s.getId(), 0.5F));

        Score result = service.getScoresForSchedule(s.getId());

        assertEquals(result, score);
    }

    @Test
    @DisplayName("If the schedule does not exist, NoSuchElementException is thrown")
    void noSuchSchedule() {
        assertThrows(
                NoSuchElementException.class,
                () -> service.getScoresForSchedule(-1L)
        );
    }

    @Test
    @DisplayName("If the score calculation has not finished yet, IllegalStateException is thrown")
    void scoresNotReady() {
        Schedule s = new Schedule("calculating-schedule");
        s.setStatus(ScheduleStatus.CALCULATING);

        scheduleRepository.save(s);

        assertThrows(
                IllegalStateException.class,
                () -> service.getScoresForSchedule(s.getId())
        );
    }
}

package com.iscte.mei.ads.schedules.api.jobs;

import com.iscte.mei.ads.schedules.api.entities.Score;
import com.iscte.mei.ads.schedules.api.repositories.ScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@DisplayName("Calculate Scores Job tests")
public class CalculateScoresJobTests {

    @Autowired
    private CalculateScoresJob job;

    @Autowired
    private ScoreRepository scoreRepository;

    @BeforeEach
    void setUp() {
        scoreRepository.deleteAll();
    }

    @Test
    @DisplayName("Creates a score")
    void createScore() {
        job.execute(1);

        assertEquals(1, scoreRepository.count());
    }

    @Test
    @DisplayName("Upserts the existing score for the given schedule")
    void updateScore() {
        Score s = new Score(1, 0.5F, 10, 1);
        scoreRepository.save(s);

        job.execute(1);

        assertEquals(1, scoreRepository.count());
        assertNotEquals(s.getNrUsedRooms(), getScore().getNrUsedRooms());
    }

    private Score getScore() {
        return scoreRepository
                .findAll()
                .iterator()
                .next();
    }

}

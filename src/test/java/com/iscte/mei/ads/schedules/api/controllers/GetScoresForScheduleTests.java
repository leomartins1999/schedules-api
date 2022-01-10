package com.iscte.mei.ads.schedules.api.controllers;

import com.iscte.mei.ads.schedules.api.deserializers.LectureListDeserializer;
import com.iscte.mei.ads.schedules.api.entities.Score;
import com.iscte.mei.ads.schedules.api.interactor.GetPivotedScoresInteractor;
import com.iscte.mei.ads.schedules.api.services.SchedulesService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SchedulesController.class)
@DisplayName("Get Scores for Schedule Endpoint tests")
public class GetScoresForScheduleTests {

    private static final String ENDPOINT_URL = "/schedules/1/scores";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SchedulesService service;

    @MockBean
    private LectureListDeserializer deserializer;

    @MockBean
    private GetPivotedScoresInteractor interactor;

    @Test
    @DisplayName("Gets the scores for a schedule")
    void getScore() throws Exception {
        Score score = new Score(1, 0.5F, 1, 1);

        when(service.getScoresForSchedule(score.getScheduleId())).thenReturn(score);

        mockMvc.perform(get(ENDPOINT_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.schedule_id").value(score.getScheduleId()))
                .andExpect(jsonPath("$.pct_overflowing_lectures").value(0.5));
    }

    @Test
    @DisplayName("If the schedule does not exist, responds with 404")
    void scheduleNotFound() throws Exception {
        when(service.getScoresForSchedule(1)).thenThrow(NoSuchElementException.class);

        mockMvc.perform(get(ENDPOINT_URL))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("If the schedule has not finished calculating, responds with 409")
    void scheduleNotFinishedCalculating() throws Exception {
        when(service.getScoresForSchedule(1)).thenThrow(IllegalStateException.class);

        mockMvc.perform(get(ENDPOINT_URL))
                .andExpect(status().isConflict());
    }

}

package com.iscte.mei.ads.schedules.api.controllers;

import com.iscte.mei.ads.schedules.api.deserializers.LectureListDeserializer;
import com.iscte.mei.ads.schedules.api.entities.Lecture;
import com.iscte.mei.ads.schedules.api.interactor.GetPivotedScoresInteractor;
import com.iscte.mei.ads.schedules.api.services.SchedulesService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SchedulesController.class)
@DisplayName("Get Lectures for Schedule Endpoint tests")
public class GetLecturesForScheduleTests {

    private static final String ENDPOINT_URL = "/schedules/1/lectures";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SchedulesService service;

    @MockBean
    private LectureListDeserializer deserializer;

    @MockBean
    private GetPivotedScoresInteractor interactor;

    @Test
    @DisplayName("Gets no lectures")
    void getNoLectures() throws Exception {
        List<Lecture> lectures = new ArrayList<>();

        when(service.getLecturesForSchedule(1, "MEI-PL", "2021-03-01", "2021-03-05")).thenReturn(lectures);

        mockMvc.perform(buildRequest("MEI-PL", "2021-03-01", "2021-03-05"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("Gets lectures")
    void getLectures() throws Exception {
        List<Lecture> lectures = new ArrayList<>();
        lectures.add(buildTestLecture(1, "ADS", "MEI-PL", "2021-03-01", "12:00"));
        lectures.add(buildTestLecture(1, "EUVI", "MEI-PL", "2021-03-02", "12:00"));

        when(service.getLecturesForSchedule(1, "MEI-PL", "2021-03-01", "2021-03-05")).thenReturn(lectures);

        mockMvc.perform(buildRequest("MEI-PL", "2021-03-01", "2021-03-05"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].lecture").value("ADS"))
                .andExpect(jsonPath("$[1].lecture").value("EUVI"));
    }

    @Test
    @DisplayName("Responds with 400 if no class query parameter was passed")
    void noClassQueryParameter() throws Exception {
        mockMvc.perform(buildRequest(null, "2021-03-01", "2021-03-05"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Responds with 400 if no start_date query parameter was passed")
    void noStartDateQueryParameter() throws Exception {
        mockMvc.perform(buildRequest("MEI-PL", null, "2021-03-05"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Responds with 400 if no end_date query parameter was passed")
    void noEndDateQueryParameter() throws Exception {
        mockMvc.perform(buildRequest("MEI-PL", "2021-03-01", null))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Responds with 404 if NoSuchElementException was thrown")
    void noSuchElementThrown() throws Exception {
        when(service.getLecturesForSchedule(anyLong(), any(), any(), any())).thenThrow(NoSuchElementException.class);

        mockMvc.perform(buildRequest("MEI-PL", "2021-03-01", "2021-03-05"))
                .andExpect(status().isNotFound());
    }

    private MockHttpServletRequestBuilder buildRequest(String klass, String startDate, String endDate) {
        MockHttpServletRequestBuilder builder = get(ENDPOINT_URL);

        appendQueryParameter(builder, "klass", klass);
        appendQueryParameter(builder, "start_date", startDate);
        appendQueryParameter(builder, "end_date", endDate);

        return builder;
    }

    private void appendQueryParameter(MockHttpServletRequestBuilder builder, String paramName, String paramValue) {
        if (paramValue != null) {
            builder.queryParam(paramName, paramValue);
        }
    }

    private Lecture buildTestLecture(long scheduleId, String lecture, String klass, String day, String startTime) {
        return new Lecture(
                lecture,
                "",
                klass,
                "",
                "",
                day,
                startTime,
                "14:00:00",
                0,
                0,
                "",
                "",
                false,
                false
        ).withScheduleId(scheduleId);
    }
}

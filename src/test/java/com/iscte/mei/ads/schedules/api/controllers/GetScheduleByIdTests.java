package com.iscte.mei.ads.schedules.api.controllers;

import com.iscte.mei.ads.schedules.api.deserializers.LectureListDeserializer;
import com.iscte.mei.ads.schedules.api.entities.Schedule;
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
@DisplayName("Get Schedule by Id Endpoint tests")
public class GetScheduleByIdTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SchedulesService service;

    @MockBean
    private LectureListDeserializer deserializer;

    @MockBean
    private GetPivotedScoresInteractor interactor;

    @Test
    @DisplayName("Gets a schedule")
    public void getScheduleById() throws Exception {
        Schedule s = new Schedule("my-schedule");

        when(service.getScheduleById(1)).thenReturn(s);

        mockMvc.perform(get("/schedules/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(s.getName()));
    }

    @Test
    @DisplayName("If the schedule does not exist not found is yield")
    public void getNonExistingSchedule() throws Exception {
        when(service.getScheduleById(1)).thenThrow(NoSuchElementException.class);

        mockMvc.perform(get("/schedules/1"))
                .andExpect(status().isNotFound());
    }

}

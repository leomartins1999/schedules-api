package com.iscte.mei.ads.schedules.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iscte.mei.ads.schedules.api.entities.Schedule;
import com.iscte.mei.ads.schedules.api.models.WriteSchedule;
import com.iscte.mei.ads.schedules.api.services.SchedulesService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SchedulesController.class)
public class CreateScheduleTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SchedulesService service;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("Creating valid schedule yields created")
    public void createScheduleTest() throws Exception {
        Schedule schedule = new Schedule("my-schedule");

        when(service.createSchedule(any())).thenReturn(schedule);

        WriteSchedule model = new WriteSchedule(schedule.getName());

        mockMvc.perform(buildCreateScheduleRequest(model))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(schedule.getName()));
    }

    @Test
    @DisplayName("Create schedule with no body yields bad request")
    public void createInvalidScheduleTest() throws Exception {
        mockMvc.perform(post("/schedules"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Create schedule with null name yields bad request")
    public void createScheduleWithNullNameTest() throws Exception {
        WriteSchedule model = new WriteSchedule(null);

        mockMvc.perform(buildCreateScheduleRequest(model))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Create schedule with blank name yields bad request")
    public void createScheduleWithBlankNameTest() throws Exception {
        WriteSchedule model = new WriteSchedule("");

        mockMvc.perform(buildCreateScheduleRequest(model))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Create schedule with empty name yields bad request")
    public void createScheduleWithEmptyNameTest() throws Exception {
        WriteSchedule model = new WriteSchedule(" ");

        mockMvc.perform(buildCreateScheduleRequest(model))
                .andExpect(status().isBadRequest());
    }

    private RequestBuilder buildCreateScheduleRequest(WriteSchedule model) throws JsonProcessingException {
        return post("/schedules")
                .content(mapper.writeValueAsString(model))
                .contentType(MediaType.APPLICATION_JSON);
    }

}
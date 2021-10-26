package com.iscte.mei.ads.schedules.api.controllers;

import com.iscte.mei.ads.schedules.api.entities.Schedule;
import com.iscte.mei.ads.schedules.api.services.SchedulesService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SchedulesController.class)
public class GetSchedulesTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SchedulesService service;

    @Test
    @DisplayName("Gets no schedules")
    public void getNoSchedulesTest() throws Exception {
        when(service.getSchedules()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/schedules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("Gets multiple schedules")
    public void getSchedulesTest() throws Exception {
        List<Schedule> schedules = new ArrayList<>();
        schedules.add(new Schedule("first-schedule"));
        schedules.add(new Schedule("second-schedule"));

        when(service.getSchedules()).thenReturn(schedules);

        mockMvc.perform(get("/schedules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value(schedules.get(0).getName()))
                .andExpect(jsonPath("$[1].name").value(schedules.get(1).getName()));
    }

}

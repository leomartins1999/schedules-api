package com.iscte.mei.ads.schedules.api.controllers;

import com.iscte.mei.ads.schedules.api.deserializers.LectureListDeserializer;
import com.iscte.mei.ads.schedules.api.services.SchedulesService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SchedulesController.class)
public class GetDatesForScheduleTests {

    private static final String ENDPOINT_URL = "/schedules/1/dates";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SchedulesService service;

    @MockBean
    private LectureListDeserializer deserializer;

    @Test
    @DisplayName("Gets no dates")
    void getNoDatesTest() throws Exception {
        Iterable<String> dates = Collections.emptyList();

        when(service.getDatesForSchedule(1)).thenReturn(dates);

        mockMvc.perform(get(ENDPOINT_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("Gets multiple dates")
    void getDatesTest() throws Exception {
        Iterable<String> dates = List.of(new String[]{"2021-03-03", "2021-03-04"});

        when(service.getDatesForSchedule(1)).thenReturn(dates);

        mockMvc.perform(get(ENDPOINT_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0]").value("2021-03-03"));
    }

    @Test
    @DisplayName("Responds with 404 if the schedule is not found")
    void noScheduleTest() throws Exception {
        when(service.getDatesForSchedule(1)).thenThrow(NoSuchElementException.class);

        mockMvc.perform(get(ENDPOINT_URL))
                .andExpect(status().isNotFound());
    }

}

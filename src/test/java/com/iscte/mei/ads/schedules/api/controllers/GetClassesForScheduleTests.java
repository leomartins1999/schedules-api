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
@DisplayName("Get Classes for Schedule Endpoint tests")
public class GetClassesForScheduleTests {

    private static final String ENDPOINT_URL = "/schedules/1/classes";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SchedulesService service;

    @MockBean
    private LectureListDeserializer deserializer;

    @Test
    @DisplayName("Gets no classes")
    void getNoDatesTest() throws Exception {
        Iterable<String> classes = Collections.emptyList();

        when(service.getClassesForSchedule(1)).thenReturn(classes);

        mockMvc.perform(get(ENDPOINT_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("Gets multiple classes")
    void getDatesTest() throws Exception {
        Iterable<String> classes = List.of(new String[]{"ADS", "EUVI"});

        when(service.getClassesForSchedule(1)).thenReturn(classes);

        mockMvc.perform(get(ENDPOINT_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0]").value("ADS"))
                .andExpect(jsonPath("$[1]").value("EUVI"));
    }

    @Test
    @DisplayName("Responds with 404 if the schedule is not found")
    void noScheduleTest() throws Exception {
        when(service.getClassesForSchedule(1)).thenThrow(NoSuchElementException.class);

        mockMvc.perform(get(ENDPOINT_URL))
                .andExpect(status().isNotFound());
    }

}

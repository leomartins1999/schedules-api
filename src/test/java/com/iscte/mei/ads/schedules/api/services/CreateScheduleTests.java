package com.iscte.mei.ads.schedules.api.services;

import com.iscte.mei.ads.schedules.api.entities.Lecture;
import com.iscte.mei.ads.schedules.api.entities.Schedule;
import com.iscte.mei.ads.schedules.api.repositories.LecturesRepository;
import com.iscte.mei.ads.schedules.api.repositories.SchedulesRepository;
import com.iscte.mei.ads.schedules.api.utils.IterableUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CreateScheduleTests {

    private final SchedulesService service;

    private final SchedulesRepository schedulesRepository;
    private final LecturesRepository lecturesRepository;

    @Autowired
    public CreateScheduleTests(SchedulesService service, SchedulesRepository schedulesRepository, LecturesRepository lecturesRepository) {
        this.service = service;
        this.schedulesRepository = schedulesRepository;
        this.lecturesRepository = lecturesRepository;
    }

    @BeforeEach
    public void setup() {
        schedulesRepository.deleteAll();
        lecturesRepository.deleteAll();
    }

    @Nested
    @DisplayName("Schedule Tests")
    class ScheduleTests {
        @Test
        @DisplayName("Create a Schedule")
        public void createScheduleTest() {
            Schedule schedule = new Schedule("schedule-name");

            Schedule result = service.createSchedule(schedule, Collections.emptyList());

            assertEquals("schedule-name", result.getName());

            List<Schedule> schedules = IterableUtils.iterableToList(schedulesRepository.findAll());
            assertEquals(1, schedules.size());
            assertEquals("schedule-name", schedules.get(0).getName());
        }

        @Test
        @DisplayName("Create schedules with equal names")
        public void createMultipleSchedulesTest() {
            Schedule firstSchedule = new Schedule("schedule-name");
            Schedule secondSchedule = new Schedule("schedule-name");

            Schedule first = service.createSchedule(firstSchedule, Collections.emptyList());
            Schedule second = service.createSchedule(secondSchedule, Collections.emptyList());

            assertEquals("schedule-name", first.getName());
            assertEquals("schedule-name", second.getName());
            assert (!first.equals(second));

            List<Schedule> schedules = IterableUtils.iterableToList(schedulesRepository.findAll());
            assertEquals(2, schedules.size());
        }
    }

    @Nested
    @DisplayName("Lecture Tests")
    class LectureTests {

        @Test
        @DisplayName("Creates no lectures")
        void createNoLectures() {
            List<Lecture> lectures = buildLectureList(0);

            service.createSchedule(
                    new Schedule("schedule-name"),
                    lectures
            );

            assertEquals(lectures.size(), lecturesRepository.count());
        }

        @Test
        @DisplayName("Creates a lecture")
        void createLecture() {
            List<Lecture> lectures = buildLectureList(1);

            service.createSchedule(
                    new Schedule("schedule-name"),
                    lectures
            );

            assertEquals(lectures.size(), lecturesRepository.count());
        }

        @Test
        @DisplayName("Creates Multiple Lectures")
        void createMultipleLectures() {
            List<Lecture> lectures = buildLectureList(5);

            service.createSchedule(
                    new Schedule("schedule-name"),
                    lectures
            );

            assertEquals(lectures.size(), lecturesRepository.count());
        }

        private List<Lecture> buildLectureList(int size) {
            ArrayList<Lecture> result = new ArrayList<>();

            for (int i = 0; i < size; i++) result.add(buildLecture());

            return result;
        }

        private Lecture buildLecture() {
            return new Lecture(
                    "Contabilidade Financeira I",
                    "LG",
                    "GAi",
                    "L0638-2T01",
                    "AA2.26",
                    "2015-10-06",
                    "11:00:00",
                    "12:30:00",
                    30,
                    38,
                    "Sala de Aulas normal",
                    "Sala Aulas Mestrado Plus, Hor�rio sala vis�vel portal p�blico, Sala Aulas Mestrado, Sala de Aulas normal",
                    false,
                    false
            );
        }
    }
}

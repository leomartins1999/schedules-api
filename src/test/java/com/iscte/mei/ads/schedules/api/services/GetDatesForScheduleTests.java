package com.iscte.mei.ads.schedules.api.services;

import com.iscte.mei.ads.schedules.api.entities.Lecture;
import com.iscte.mei.ads.schedules.api.entities.Schedule;
import com.iscte.mei.ads.schedules.api.repositories.LectureRepository;
import com.iscte.mei.ads.schedules.api.repositories.ScheduleRepository;
import com.iscte.mei.ads.schedules.api.utils.IterableUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayName("Get Dates for Schedule Service tests")
public class GetDatesForScheduleTests {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private SchedulesService service;

    private long scheduleId;

    @BeforeEach
    public void setup() {
        lectureRepository.deleteAll();
        scheduleRepository.deleteAll();

        Schedule s = scheduleRepository.save(new Schedule("test-schedule"));
        scheduleId = s.getId();
    }

    @Test
    @DisplayName("Gets date for schedule")
    void datesForScheduleTest() {
        Lecture l = lectureRepository.save(buildTestLecture(scheduleId, "2021-03-03"));

        Iterable<String> dates = service.getDatesForSchedule(scheduleId);
        List<String> dateList = IterableUtils.iterableToList(dates);

        assertEquals(1, dateList.size());
        assertEquals(l.getDay(), dateList.get(0));
    }

    @Test
    @DisplayName("Dates are unique")
    void datesAreUniqueTest() {
        String day = "2021-03-03";
        Lecture l = buildTestLecture(scheduleId, day);

        lectureRepository.save(l);
        lectureRepository.save(l);

        Iterable<String> dates = service.getDatesForSchedule(scheduleId);
        List<String> dateList = IterableUtils.iterableToList(dates);

        assertEquals(1, dateList.size());
        assertEquals(day, dateList.get(0));
    }

    @Test
    @DisplayName("Dates are ordered (from oldest to most recent)")
    void datesAreOrderedTest() {
        String thirdOfMarch = "2021-03-03";
        String fourthOfMarch = "2021-03-04";
        String thirdOfMay = "2021-05-03";

        lectureRepository.save(buildTestLecture(scheduleId, thirdOfMay));
        lectureRepository.save(buildTestLecture(scheduleId, thirdOfMarch));
        lectureRepository.save(buildTestLecture(scheduleId, fourthOfMarch));

        Iterable<String> dates = service.getDatesForSchedule(scheduleId);
        List<String> dateList = IterableUtils.iterableToList(dates);

        assertEquals(3, dateList.size());
        assertEquals(thirdOfMarch, dateList.get(0));
        assertEquals(fourthOfMarch, dateList.get(1));
        assertEquals(thirdOfMay, dateList.get(2));
    }

    @Test
    @DisplayName("Returns no dates if there are none")
    void noDatesTest() {
        Iterable<String> dates = service.getDatesForSchedule(scheduleId);

        assertFalse(dates.iterator().hasNext());
    }

    @Test
    @DisplayName("Throws no such element exception if the schedule does not exist")
    void nonExistingScheduleTest() {
        assertThrows(
                NoSuchElementException.class,
                () -> service.getDatesForSchedule(-1L)
        );
    }

    private Lecture buildTestLecture(long scheduleId, String day) {
        return new Lecture(
                "",
                "",
                "",
                "",
                "",
                day,
                "11:00:00",
                "12:30:00",
                0,
                0,
                "",
                "",
                false,
                false
        ).withScheduleId(scheduleId);
    }
}

package com.iscte.mei.ads.schedules.api.services;

import com.iscte.mei.ads.schedules.api.entities.Lecture;
import com.iscte.mei.ads.schedules.api.entities.Schedule;
import com.iscte.mei.ads.schedules.api.repositories.LectureRepository;
import com.iscte.mei.ads.schedules.api.repositories.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayName("Get Lectures for Schedule Service tests")
public class GetLecturesForScheduleTests {

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
    @DisplayName("Get a lecture")
    void getLecture() {
        Lecture l = lectureRepository.save(buildTestLecture(scheduleId, "ABC", "2021-03-02", "11:00:00"));

        List<Lecture> result = service.getLecturesForSchedule(scheduleId, "ABC", "2021-03-01", "2021-03-05");

        assertEquals(1, result.size());
        assertEquals(l, result.get(0));
    }

    @Test
    @DisplayName("Lectures are ordered by date and time")
    void getOrderedLectures() {
        Lecture thirdLecture = lectureRepository.save(buildTestLecture(scheduleId, "ABC", "2021-03-02", "12:30:00"));
        Lecture firstLecture = lectureRepository.save(buildTestLecture(scheduleId, "ABC", "2021-03-01", "11:00:00"));
        Lecture secondLecture = lectureRepository.save(buildTestLecture(scheduleId, "ABC", "2021-03-02", "11:00:00"));

        List<Lecture> result = service.getLecturesForSchedule(scheduleId, "ABC", "2021-03-01", "2021-03-05");

        assertEquals(3, result.size());
        assertEquals(firstLecture, result.get(0));
        assertEquals(secondLecture, result.get(1));
        assertEquals(thirdLecture, result.get(2));
    }

    @Test
    @DisplayName("Lectures with composed classes are fetched")
    void getComposedLecture() {
        Lecture abcLecture = lectureRepository.save(buildTestLecture(scheduleId, "ABC", "2021-03-01", "11:00:00"));
        Lecture defAbcLecture = lectureRepository.save(buildTestLecture(scheduleId, "DEF, ABC", "2021-03-01", "12:30:00"));

        List<Lecture> result = service.getLecturesForSchedule(scheduleId, "ABC", "2021-03-01", "2021-03-05");

        assertEquals(2, result.size());
        assertEquals(abcLecture, result.get(0));
        assertEquals(defAbcLecture, result.get(1));
    }

    @Test
    @DisplayName("Gets no lectures")
    void getNoLectures() {
        List<Lecture> result = service.getLecturesForSchedule(scheduleId, "ABC", "2021-03-01", "2021-03-05");

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Throws NoSuchElementException if the schedule does not exist")
    void nonExistingScheduleTest() {
        assertThrows(
                NoSuchElementException.class,
                () -> service.getLecturesForSchedule(-1L, "klass", "2021-03-01", "2021-03-05")
        );
    }

    private Lecture buildTestLecture(long scheduleId, String klass, String day, String startTime) {
        return new Lecture(
                "",
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

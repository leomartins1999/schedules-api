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

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayName("Get Classes for Schedule Service tests")
public class GetClassesForScheduleTests {

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
    @DisplayName("Gets class for schedule")
    void getKlass() {
        Lecture l = lectureRepository.save(buildTestLecture(scheduleId, "MEI-PL"));

        Iterator<String> resultIterator = service
                .getClassesForSchedule(scheduleId)
                .iterator();

        assertEquals(l.getKlass(), resultIterator.next());
        assertFalse(resultIterator.hasNext());
    }

    @Test
    @DisplayName("Gets no classes")
    void getNoClasses() {
        Iterator<String> resultIterator = service
                .getClassesForSchedule(scheduleId)
                .iterator();

        assertFalse(resultIterator.hasNext());
    }

    @Test
    @DisplayName("Classes are unique and ordered")
    void classesAreUnique() {
        Lecture abcLecture = lectureRepository.save(buildTestLecture(scheduleId, "ABC"));

        Lecture defLecture = lectureRepository.save(buildTestLecture(scheduleId, "DEF"));

        Lecture ghiLecture = lectureRepository.save(buildTestLecture(scheduleId, "GHI"));
        lectureRepository.save(buildTestLecture(scheduleId, "GHI"));

        Iterator<String> resultIterator = service
                .getClassesForSchedule(scheduleId)
                .iterator();

        assertEquals(abcLecture.getKlass(), resultIterator.next());
        assertEquals(defLecture.getKlass(), resultIterator.next());
        assertEquals(ghiLecture.getKlass(), resultIterator.next());
        assertFalse(resultIterator.hasNext());
    }

    @Test
    @DisplayName("Composed classes (ABC, DEF) are flattened")
    void composedClassesAreFlattened() {
        lectureRepository.save(buildTestLecture(scheduleId, "XPTO"));
        lectureRepository.save(buildTestLecture(scheduleId, "ABC, DEF"));

        Iterator<String> resultIterator = service
                .getClassesForSchedule(scheduleId)
                .iterator();

        assertEquals("ABC", resultIterator.next());
        assertEquals("DEF", resultIterator.next());
        assertEquals("XPTO", resultIterator.next());
        assertFalse(resultIterator.hasNext());
    }

    @Test
    @DisplayName("If the schedule does not exist, NoSuchElementException is thrown")
    void fakeScheduleThrowsException() {
        assertThrows(
                NoSuchElementException.class,
                () -> service.getDatesForSchedule(-1L)
        );
    }

    private Lecture buildTestLecture(long scheduleId, String klass) {
        return new Lecture(
                "",
                "",
                klass,
                "",
                "",
                "2021-03-03",
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

package com.iscte.mei.ads.schedules.api.jobs;

import com.iscte.mei.ads.schedules.api.entities.Lecture;
import com.iscte.mei.ads.schedules.api.repositories.LectureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("Import Lectures for Schedule tests")
public class ImportLecturesJobTests {

    private final static long SCHEDULE_ID = 1L;

    private final ImportLecturesJob job;

    private final LectureRepository repository;

    @Autowired
    public ImportLecturesJobTests(ImportLecturesJob job, LectureRepository repository) {
        this.job = job;
        this.repository = repository;
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Creates no lectures")
    void createNoLectures() {
        List<Lecture> lectures = buildLectureList(0);

        job.execute(SCHEDULE_ID, lectures);

        assertEquals(lectures.size(), repository.count());
    }

    @Test
    @DisplayName("Creates a lecture")
    void createLecture() {
        List<Lecture> lectures = buildLectureList(1);

        job.execute(SCHEDULE_ID, lectures);

        assertEquals(lectures.size(), repository.count());
    }

    @Test
    @DisplayName("Creates Multiple Lectures")
    void createMultipleLectures() {
        List<Lecture> lectures = buildLectureList(5);

        job.execute(SCHEDULE_ID, lectures);

        assertEquals(lectures.size(), repository.count());
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

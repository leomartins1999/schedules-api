package com.iscte.mei.ads.schedules.api.deserializers;

import com.iscte.mei.ads.schedules.api.models.WriteLecture;
import com.iscte.mei.ads.schedules.api.entities.ScheduleFormat;
import com.iscte.mei.ads.schedules.api.utils.IterableUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class WriteLectureListDeserializerTests {

    private static final Base64.Encoder encoder = Base64.getEncoder();

    private final LectureListDeserializer deserializer;

    @Autowired
    public WriteLectureListDeserializerTests(LectureListDeserializer deserializer) {
        this.deserializer = deserializer;
    }

    @Test
    @DisplayName("Deserializes file with a single schedule")
    public void deserializeSingleLectureTest() throws IOException {
        String content = getAndEncodedResourceContent("schedule-with-contabilidade-financeira.csv");

        Iterable<WriteLecture> result = deserializer.deserialize(content, ScheduleFormat.CSV);

        List<WriteLecture> writeLectures = IterableUtils.iterableToList(result);

        assertEquals(1, writeLectures.size());
        assertEquals("Contabilidade Financeira I", writeLectures.get(0).getLecture());
    }

    @Test
    @DisplayName("Deserializes file with many schedules")
    public void deserializesFullScheduleTest() throws IOException {
        String content = getAndEncodedResourceContent("large-schedule.csv");

        Iterable<WriteLecture> result = deserializer.deserialize(content, ScheduleFormat.CSV);

        List<WriteLecture> writeLectures = IterableUtils.iterableToList(result);

        assertEquals(23981, writeLectures.size());
    }

    @Test
    @DisplayName("Throws exception when schedule is invalid")
    public void throwsExceptionWhenScheduleIsInvalidTest() throws IOException {
        String content = getAndEncodedResourceContent("invalid-schedule.csv");

        assertThrows(
                IllegalArgumentException.class,
                () -> deserializer.deserialize(content, ScheduleFormat.CSV)
        );
    }

    @Test
    @DisplayName("Throws exception when file has no lectures")
    public void deserializesNoLecturesTest() throws IOException {
        String content = getAndEncodedResourceContent("empty-schedule.csv");

        assertThrows(
                IllegalArgumentException.class,
                () -> deserializer.deserialize(content, ScheduleFormat.CSV)
        );
    }


    private String getAndEncodedResourceContent(String filename) throws IOException {
        byte[] content = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("test-schedules/" + filename)
                .readAllBytes();

        return encoder.encodeToString(content);
    }

}

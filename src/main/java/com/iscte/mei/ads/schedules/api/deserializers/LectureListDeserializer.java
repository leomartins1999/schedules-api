package com.iscte.mei.ads.schedules.api.deserializers;

import com.iscte.mei.ads.schedules.api.models.WriteLecture;
import com.iscte.mei.ads.schedules.api.models.ScheduleFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LectureListDeserializer {

    private final CSVLectureListDeserializer csvLectureListDeserializer;

    @Autowired
    public LectureListDeserializer(CSVLectureListDeserializer csvLectureListDeserializer) {
        this.csvLectureListDeserializer = csvLectureListDeserializer;
    }

    public Iterable<WriteLecture> deserialize(String content, ScheduleFormat format) throws IOException {
        switch (format) {
            case CSV:
                return csvLectureListDeserializer.deserialize(content);
            default:
                throw new IllegalArgumentException();
        }
    }

}

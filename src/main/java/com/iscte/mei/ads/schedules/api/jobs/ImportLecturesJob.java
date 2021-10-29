package com.iscte.mei.ads.schedules.api.jobs;

import com.iscte.mei.ads.schedules.api.entities.Lecture;
import com.iscte.mei.ads.schedules.api.repositories.LecturesRepository;
import com.iscte.mei.ads.schedules.api.utils.IterableUtils;
import org.springframework.stereotype.Component;

@Component
public class ImportLecturesJob {

    private final LecturesRepository repository;

    public ImportLecturesJob(LecturesRepository repository) {
        this.repository = repository;
    }

    public void execute(long scheduleId, Iterable<Lecture> lectures) {
        Iterable<Lecture> entities = IterableUtils.map(lectures, (lecture -> lecture.withScheduleId(scheduleId)));

        repository.saveAll(entities);
    }
}

package com.iscte.mei.ads.schedules.api.executors;

import com.iscte.mei.ads.schedules.api.entities.Lecture;
import com.iscte.mei.ads.schedules.api.entities.Schedule;
import com.iscte.mei.ads.schedules.api.entities.ScheduleStatus;
import com.iscte.mei.ads.schedules.api.jobs.ImportLecturesJob;
import com.iscte.mei.ads.schedules.api.properties.ImportScheduleProperties;
import com.iscte.mei.ads.schedules.api.repositories.SchedulesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ImportScheduleExecutor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ImportLecturesJob importJob;
    private final SchedulesRepository repository;

    private final ExecutorService pool;

    public ImportScheduleExecutor(ImportLecturesJob importJob, SchedulesRepository repository, ImportScheduleProperties properties) {
        this.importJob = importJob;
        this.repository = repository;

        this.pool = Executors.newFixedThreadPool(properties.getMaxParallel());
    }

    public void scheduleImport(Schedule schedule, Iterable<Lecture> lectures) {
        pool.submit(() -> importSchedule(schedule, lectures));
    }

    private void importSchedule(Schedule schedule, Iterable<Lecture> lectures) {
        try {
            importLectures(schedule, lectures);
            calculateScores(schedule);

            logger.info(String.format("Completed processing for Schedule %d", schedule.getId()));
            updateScheduleStatus(schedule, ScheduleStatus.DONE);
        } catch (Exception ex) {
            logger.error(String.format("Thrown exception importing schedule %d", schedule.getId()), ex);
            updateScheduleStatus(schedule, ScheduleStatus.ERROR);
        }
    }

    private void importLectures(Schedule schedule, Iterable<Lecture> lectures) {
        logger.info(String.format("Importing lectures for Schedule %d", schedule.getId()));
        updateScheduleStatus(schedule, ScheduleStatus.IMPORTING);
        importJob.execute(schedule.getId(), lectures);
    }

    private void calculateScores(Schedule schedule) {
        logger.info(String.format("Calculating scores for Schedule %d", schedule.getId()));
        updateScheduleStatus(schedule, ScheduleStatus.CALCULATING);
        // todo: call calculate scores job
    }

    private void updateScheduleStatus(Schedule schedule, ScheduleStatus status) {
        schedule.setStatus(status);
        repository.save(schedule);
    }

}

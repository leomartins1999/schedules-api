package com.iscte.mei.ads.schedules.api.listeners;

import com.iscte.mei.ads.schedules.api.entities.Schedule;
import com.iscte.mei.ads.schedules.api.executors.ScheduleJobsExecutor;
import com.iscte.mei.ads.schedules.api.repositories.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Fetches schedules in 'CALCULATING' status and
 * enqueues them for calculation
 * <p>
 * This will be used to trigger already calculated schedules to
 * redetermine their scores when a new metric is added
 * <p>
 * When a migration that adds a new score to the ScheduleScores
 * table is added, this migration MUST also include a query
 * updating all schedule records that have the 'DONE' status to
 * have the 'CALCULATING' status
 */
@Component
public class PendingSchedulesEnqueuer implements ApplicationListener<ApplicationReadyEvent> {

    private final Logger logger = LoggerFactory.getLogger(PendingSchedulesEnqueuer.class);

    private final ScheduleJobsExecutor executor;

    private final ScheduleRepository repository;

    public PendingSchedulesEnqueuer(ScheduleJobsExecutor executor, ScheduleRepository repository) {
        this.executor = executor;
        this.repository = repository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        List<Schedule> schedules = repository.getCalculatingSchedules();

        logger.info("Re-enqueuing {} schedules for calculation", schedules.size());
        for (Schedule s : schedules) executor.scheduleCalculation(s);
    }

}

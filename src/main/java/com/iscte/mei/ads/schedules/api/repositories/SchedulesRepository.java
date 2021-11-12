package com.iscte.mei.ads.schedules.api.repositories;

import com.iscte.mei.ads.schedules.api.entities.Schedule;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface SchedulesRepository extends CrudRepository<Schedule, Long> {

    @Query("SELECT DISTINCT day FROM lecture WHERE schedule_id = :scheduleId ORDER BY day ASC")
    Iterable<String> getDatesForSchedule(long scheduleId);

}

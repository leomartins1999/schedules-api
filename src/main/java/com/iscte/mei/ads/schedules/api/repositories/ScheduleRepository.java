package com.iscte.mei.ads.schedules.api.repositories;

import com.iscte.mei.ads.schedules.api.entities.Schedule;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
    @Query("SELECT * FROM schedule WHERE status = 'CALCULATING';")
    List<Schedule> getCalculatingSchedules();

    @Query("SELECT * FROM schedule ORDER BY name ASC;")
    List<Schedule> getSchedulesOrderedByName();
}

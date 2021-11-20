package com.iscte.mei.ads.schedules.api.repositories;

import com.iscte.mei.ads.schedules.api.entities.Score;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface ScoreRepository extends CrudRepository<Score, Long> {

    @Query("SELECT * FROM score WHERE schedule_id = :scheduleId;")
    Score getByScheduleId(long scheduleId);

    @Modifying
    @Query("DELETE FROM score WHERE schedule_id = :scheduleId;")
    void deleteForScheduleId(long scheduleId);
}

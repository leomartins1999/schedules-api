package com.iscte.mei.ads.schedules.api.repositories;

import com.iscte.mei.ads.schedules.api.entities.Lecture;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface LecturesRepository extends CrudRepository<Lecture, Long> {

    @Query("SELECT " +
            "CAST(COUNT(*) FILTER ( WHERE signed_up_for_class > max_number_of_students_for_room ) AS DECIMAL) " +
            "/ GREATEST(COUNT(*), 1) " +
            "FROM lecture " +
            "WHERE schedule_id = :scheduleId;")
    float getPctOverflowingLectures(long scheduleId);

}

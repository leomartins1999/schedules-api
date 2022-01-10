package com.iscte.mei.ads.schedules.api.repositories;

import com.iscte.mei.ads.schedules.api.entities.Lecture;
import com.iscte.mei.ads.schedules.api.models.DatePeriod;
import com.iscte.mei.ads.schedules.api.models.aggregatescores.AggregateScoresByWeek;
import com.iscte.mei.ads.schedules.api.models.aggregatescores.AggregateScoresByWeekday;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface LectureRepository extends CrudRepository<Lecture, Long> {

    @Query("SELECT DISTINCT day FROM lecture WHERE schedule_id = :scheduleId ORDER BY day ASC;")
    List<String> getDatesForSchedule(long scheduleId);

    @Query("SELECT DISTINCT klass FROM lecture WHERE schedule_id = :scheduleId ORDER BY klass ASC;")
    List<String> getClassesForSchedule(long scheduleId);

    @Query("SELECT * "
            + "FROM lecture "
            + "WHERE schedule_id = :scheduleId "
            + "AND klass ~ :klass "
            + "AND day >= :startDate "
            + "AND day <= :endDate "
            + "ORDER BY day ASC, start_time ASC;"
    )
    List<Lecture> getLecturesForSchedule(long scheduleId, String klass, String startDate, String endDate);

    @Query("SELECT COUNT(DISTINCT room) FROM lecture WHERE schedule_id = :scheduleId;")
    long getNrUsedRooms(long scheduleId);

    @Query(
            "SELECT "
                    + "CAST(COUNT(*) FILTER ( WHERE is_room_overqualified_for_class ) AS DECIMAL) "
                    + "/ GREATEST(COUNT(*), 1) "
                    + "FROM lecture "
                    + "WHERE schedule_id = :scheduleId;"
    )
    float getPctOverqualifiedRoomsForLectures(long scheduleId);

    @Query("SELECT "
            + "CAST(COUNT(*) FILTER ( WHERE signed_up_for_class > max_number_of_students_for_room ) AS DECIMAL) "
            + "/ GREATEST(COUNT(*), 1) "
            + "FROM lecture "
            + "WHERE schedule_id = :scheduleId;")
    float getPctOverflowingLectures(long scheduleId);

    @Query("SELECT MIN(day) AS startDate, MAX(day) AS endDate FROM lecture WHERE schedule_id = :scheduleId;")
    DatePeriod getDatePeriod(long scheduleId);

    @Query("SELECT "
            + "EXTRACT(dow FROM day) AS weekIdx, "
            + "COALESCE(COUNT(DISTINCT room), 0) AS nrUsedRooms, "
            + "COALESCE(CAST(COUNT(*) FILTER ( WHERE is_room_overqualified_for_class ) AS DECIMAL) / GREATEST(COUNT(*), 1), 0) as pctOverqualifiedRoomsForLectures, "
            + "COALESCE(CAST(COUNT(*) FILTER ( WHERE signed_up_for_class > max_number_of_students_for_room ) AS DECIMAL) / GREATEST(COUNT(*), 1), 0) as pctOverflowingLectures, "
            + "COALESCE(COUNT(1), 0) as nrLectures "
            + "FROM lecture "
            + "WHERE schedule_id = :scheduleId "
            + "AND day IS NOT NULL "
            + "GROUP BY 1 "
            + "ORDER BY 1;"
    )
    AggregateScoresByWeekday[] getScoresByWeekday(long scheduleId);

    @Query("SELECT "
            + ":week AS week, "
            + "COALESCE(COUNT(DISTINCT room), 0) AS nrUsedRooms, "
            + "COALESCE(CAST(COUNT(*) FILTER ( WHERE is_room_overqualified_for_class ) AS DECIMAL) / GREATEST(COUNT(*), 1), 0) as pctOverqualifiedRoomsForLectures, "
            + "COALESCE(CAST(COUNT(*) FILTER ( WHERE signed_up_for_class > max_number_of_students_for_room ) AS DECIMAL) / GREATEST(COUNT(*), 1), 0) as pctOverflowingLectures, "
            + "COALESCE(COUNT(1), 0) as nrLectures "
            + "FROM lecture "
            + "WHERE schedule_id = :scheduleId "
            + "AND day >= :startDate AND day < :endDate;"
    )
    AggregateScoresByWeek getScoresByWeek(long scheduleId, String week, Date startDate, Date endDate);
}

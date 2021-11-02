package com.iscte.mei.ads.schedules.api.repositories;

import com.iscte.mei.ads.schedules.api.entities.Lecture;
import org.springframework.data.repository.CrudRepository;

public interface LecturesRepository extends CrudRepository<Lecture, Long> {

	@Query("SELECT klass FROM lecture l WHERE lecture.scheduleId = schedule.id")
	public List<klass> getByScheduleId(Schedule schedule)

}

package com.iscte.mei.ads.schedules.api.entities;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class Lecture {

    @Id
    private long id;
    private long scheduleId;

    private String lecture;
    private String course;
    private String klass;
    private String shift;
    private String room;

    private String day;

    private String startTime;
    private String endTime;

    private int signedUpForClass;
    private int maxNumberOfStudentsForRoom;

    private String lectureRoomRequestedCharacteristics;
    private String lectureRoomActualCharacteristics;

    private boolean isRoomOverqualifiedForClass;
    private boolean shiftHasTooManyStudentsForRoom;

    public Lecture(String lecture, String course, String klass, String shift, String room, String day, String startTime, String endTime, int signedUpForClass, int maxNumberOfStudentsForRoom, String lectureRoomRequestedCharacteristics, String lectureRoomActualCharacteristics, boolean isRoomOverqualifiedForClass, boolean shiftHasTooManyStudentsForRoom) {
        this.lecture = lecture;
        this.course = course;
        this.klass = klass;
        this.shift = shift;
        this.room = room;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.signedUpForClass = signedUpForClass;
        this.maxNumberOfStudentsForRoom = maxNumberOfStudentsForRoom;
        this.lectureRoomRequestedCharacteristics = lectureRoomRequestedCharacteristics;
        this.lectureRoomActualCharacteristics = lectureRoomActualCharacteristics;
        this.isRoomOverqualifiedForClass = isRoomOverqualifiedForClass;
        this.shiftHasTooManyStudentsForRoom = shiftHasTooManyStudentsForRoom;
    }

    public long getId() {
        return id;
    }

    public long getScheduleId() {
        return scheduleId;
    }

    public String getLecture() {
        return lecture;
    }

    public String getCourse() {
        return course;
    }

    public String getKlass() {
        return klass;
    }

    public String getShift() {
        return shift;
    }

    public String getRoom() {
        return room;
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getSignedUpForClass() {
        return signedUpForClass;
    }

    public int getMaxNumberOfStudentsForRoom() {
        return maxNumberOfStudentsForRoom;
    }

    public String getLectureRoomRequestedCharacteristics() {
        return lectureRoomRequestedCharacteristics;
    }

    public String getLectureRoomActualCharacteristics() {
        return lectureRoomActualCharacteristics;
    }

    public boolean isRoomOverqualifiedForClass() {
        return isRoomOverqualifiedForClass;
    }

    public boolean isShiftHasTooManyStudentsForRoom() {
        return shiftHasTooManyStudentsForRoom;
    }

    public Lecture withScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecture lecture1 = (Lecture) o;
        return scheduleId == lecture1.scheduleId && signedUpForClass == lecture1.signedUpForClass && maxNumberOfStudentsForRoom == lecture1.maxNumberOfStudentsForRoom && isRoomOverqualifiedForClass == lecture1.isRoomOverqualifiedForClass && shiftHasTooManyStudentsForRoom == lecture1.shiftHasTooManyStudentsForRoom && Objects.equals(lecture, lecture1.lecture) && Objects.equals(course, lecture1.course) && Objects.equals(klass, lecture1.klass) && Objects.equals(shift, lecture1.shift) && Objects.equals(room, lecture1.room) && Objects.equals(day, lecture1.day) && Objects.equals(startTime, lecture1.startTime) && Objects.equals(endTime, lecture1.endTime) && Objects.equals(lectureRoomRequestedCharacteristics, lecture1.lectureRoomRequestedCharacteristics) && Objects.equals(lectureRoomActualCharacteristics, lecture1.lectureRoomActualCharacteristics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId, lecture, course, klass, shift, room, day, startTime, endTime, signedUpForClass, maxNumberOfStudentsForRoom, lectureRoomRequestedCharacteristics, lectureRoomActualCharacteristics, isRoomOverqualifiedForClass, shiftHasTooManyStudentsForRoom);
    }
}

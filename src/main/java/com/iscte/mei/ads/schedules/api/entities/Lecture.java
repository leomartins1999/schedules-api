package com.iscte.mei.ads.schedules.api.entities;

import org.springframework.data.annotation.Id;

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

}

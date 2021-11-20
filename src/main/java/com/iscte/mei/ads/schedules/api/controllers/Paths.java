package com.iscte.mei.ads.schedules.api.controllers;

public class Paths {

    public final static String SCHEDULES_MAPPING = "/schedules";

    public final static String SCHEDULE_BY_ID_MAPPING = "/{scheduleId}";

    public final static String CLASSES_FOR_SCHEDULE_MAPPING = SCHEDULE_BY_ID_MAPPING + "/classes";
    public final static String DATES_FOR_SCHEDULE_MAPPING = SCHEDULE_BY_ID_MAPPING + "/dates";
    public final static String LECTURES_FOR_SCHEDULE_MAPPING = SCHEDULE_BY_ID_MAPPING + "/lectures";
    public final static String SCORES_FOR_SCHEDULE_MAPPING = SCHEDULE_BY_ID_MAPPING + "/scores";

}

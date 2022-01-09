package com.iscte.mei.ads.schedules.api.models;

import org.springframework.data.relational.core.mapping.Column;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DatePeriod {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Column("startDate")
    private Date startDate;

    @Column("endDate")
    private Date endDate;

    public DatePeriod() {
    }

    public DatePeriod(LocalDate startDate, LocalDate endDate) {
        this.startDate = Date.valueOf(startDate);
        this.endDate = Date.valueOf(endDate);
    }

    public List<DatePeriod> getWeeks() {
        List<DatePeriod> res = new ArrayList<>();

        LocalDate curr = getFirstDayOfWeekForDate(startDate);
        LocalDate end = getLastDayOfWeekForDate(endDate);

        while (true) {
            LocalDate next = curr.plusWeeks(1);

            if (curr.isBefore(end)) {
                res.add(new DatePeriod(curr, next));
                curr = next;
            } else {
                res.add(new DatePeriod(curr, end.plusDays(1)));
                break;
            }
        }

        return res;
    }

    @Override
    public String toString() {
        String startDateText = startDate.toLocalDate().format(FORMATTER);
        String endDateText = endDate.toLocalDate().format(FORMATTER);

        return String.format("%s - %s", startDateText, endDateText);
    }

    private LocalDate getFirstDayOfWeekForDate(Date d) {
        LocalDate curr = d.toLocalDate();

        while (curr.getDayOfWeek() != DayOfWeek.SUNDAY) {
            curr = curr.minusDays(1);
        }

        return curr;
    }

    private LocalDate getLastDayOfWeekForDate(Date d) {
        return getFirstDayOfWeekForDate(d).plusWeeks(1);
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}

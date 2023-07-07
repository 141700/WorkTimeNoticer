package ru.massandrashop.worktimenoticer.model;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class WorktimeRecord {

    private final String workingDate;

    private final String officeTitle;

    private final LocalTime start;

    private LocalTime finish;

    public WorktimeRecord(String workingDate, String officeTitle, LocalTime start) {
        this.workingDate = workingDate;
        this.officeTitle = officeTitle;
        this.start = start;
    }

    @NotNull
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String finishString = finish != null ? finish.format(formatter) : "";
        return workingDate + "   " + officeTitle + "   " + start.format(formatter) + "   " + finishString + System.getProperty("line.separator");
    }

    public void setFinish(LocalTime finish) {
        this.finish = finish;
    }

    public String getWorkingDate() {
        return workingDate;
    }

    public String getOfficeTitle() {
        return officeTitle;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getFinish() {
        return finish;
    }

    public Duration getDuartion() {
        return finish != null ? Duration.between(start, finish) : Duration.ZERO;
    }

}

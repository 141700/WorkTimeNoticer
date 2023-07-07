package ru.massandrashop.worktimenoticer.service.db;

import android.content.Context;
import ru.massandrashop.worktimenoticer.R;
import ru.massandrashop.worktimenoticer.model.Point;
import ru.massandrashop.worktimenoticer.model.Record;
import ru.massandrashop.worktimenoticer.model.WorktimeRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;

public class WorktimeRecordService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy");

    public static Optional<String> getWorktimeRecordsForDemonstration(Context context) {
        Comparator<WorktimeRecord> comparator = Comparator.comparing(wtr -> LocalDate.parse(wtr.getWorkingDate(), DATE_TIME_FORMATTER));
        return getWorktimeRecords(context).values().stream()
                .sorted(comparator.reversed())
                .limit(Integer.parseInt(context.getString(R.string.wtr_days_max)))
                .map(WorktimeRecord::toString)
                .reduce(String::concat);
    }

    public static HashMap<String, WorktimeRecord> getWorktimeRecords(Context context) {
        HashMap<String, WorktimeRecord> allRecords = new HashMap<>();
        HashMap<String, Point> points = PointLocalDatabaseService.getPointsFromLocalDatabase(context);
        RecordLocalDatabaseService.getRecordsFromLocalDatabase(context).stream()
                .sorted(Comparator.comparing(Record::getId))
                .forEachOrdered(entry -> processRecord(allRecords, entry, points));
        return allRecords;
    }

    private static void processRecord(HashMap<String, WorktimeRecord> allRecords, Record record, HashMap<String, Point> points) {
        LocalDateTime timeStamp = record.getTimeStamp();
        String workingDay = timeStamp.format(DATE_TIME_FORMATTER);
        LocalTime recordTime = LocalTime.from(timeStamp);
        allRecords.merge(workingDay,
                new WorktimeRecord(workingDay, points.get(record.getMacAddress()).getOfficeTitle(), recordTime),
                (oldValue, newValue) -> {
                    oldValue.setFinish(recordTime);
                    return oldValue;
                });
    }

}

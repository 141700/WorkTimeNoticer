package ru.massandrashop.worktimenoticer.service.db;

import android.content.Context;
import ru.massandrashop.worktimenoticer.R;
import ru.massandrashop.worktimenoticer.dao.LocalDatabase;
import ru.massandrashop.worktimenoticer.dao.RecordDao;
import ru.massandrashop.worktimenoticer.model.Record;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class RecordLocalDatabaseService {

    public static List<Record> getRecordsFromLocalDatabase(Context context) {
        RecordDao dao = LocalDatabase.getInstance(context).recordDao();
        LocalDateTime limit = LocalDateTime.now().minusDays(Integer.parseInt(context.getString(R.string.wtr_days_max)));
        try {
            return Executors.newSingleThreadExecutor().submit(() -> dao.getAllWithLimit(limit)).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addRecordToLocalDatabase(Context context, Record record) {
        RecordDao recordDao = LocalDatabase.getInstance(context).recordDao();
        Executors.newSingleThreadExecutor().execute(() -> recordDao.insert(record));
    }

}

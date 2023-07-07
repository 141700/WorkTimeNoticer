package ru.massandrashop.worktimenoticer.dao;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import ru.massandrashop.worktimenoticer.model.Point;
import ru.massandrashop.worktimenoticer.model.Record;
import ru.massandrashop.worktimenoticer.utils.LocalDateTimeConverter;

@Database(entities = {Point.class, Record.class}, version = 1)
@TypeConverters({LocalDateTimeConverter.class})
public abstract class LocalDatabase extends RoomDatabase {

    private static LocalDatabase instance;

    private static final String LOCAL_DB_NAME = "app_local_db";

    public static synchronized LocalDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, LocalDatabase.class, LOCAL_DB_NAME).build();
        }
        return instance;
    }

    public abstract PointDao pointDao();

    public abstract RecordDao recordDao();

}

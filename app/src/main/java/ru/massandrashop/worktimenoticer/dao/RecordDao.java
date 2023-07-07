package ru.massandrashop.worktimenoticer.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import ru.massandrashop.worktimenoticer.model.Record;

import java.time.LocalDateTime;
import java.util.List;

@Dao
public interface RecordDao {

    @Insert
    void insert(Record record);

    @Query("SELECT * FROM Record ORDER BY id ASC")
    List<Record> getAll();

    @Query("SELECT * FROM Record WHERE timeStamp >= :limit ORDER BY id ASC")
    List<Record> getAllWithLimit(LocalDateTime limit);

    @Query("DELETE FROM Record")
    void deleteAll();

}

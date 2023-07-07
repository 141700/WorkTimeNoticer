package ru.massandrashop.worktimenoticer.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import ru.massandrashop.worktimenoticer.model.Point;

import java.util.List;

@Dao
public interface PointDao {

    @Insert
    void insertAll(List<Point> points);

    @Query("DELETE FROM Point")
    void deleteAll();

    @Query("SELECT * FROM Point")
    List<Point> getAll();
}


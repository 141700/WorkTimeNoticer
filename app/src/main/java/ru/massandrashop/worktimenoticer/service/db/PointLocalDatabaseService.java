package ru.massandrashop.worktimenoticer.service.db;

import android.content.Context;
import androidx.annotation.NonNull;
import ru.massandrashop.worktimenoticer.dao.LocalDatabase;
import ru.massandrashop.worktimenoticer.dao.PointDao;
import ru.massandrashop.worktimenoticer.model.Point;
import ru.massandrashop.worktimenoticer.service.PreferencesService;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class PointLocalDatabaseService {

    public static HashMap<String, Point> getPointsFromLocalDatabase(Context context) {
        PointDao dao = LocalDatabase.getInstance(context).pointDao();
        Future<List<Point>> list = Executors.newSingleThreadExecutor().submit(dao::getAll);
        try {
            return getPointHashMap(list);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @NonNull
    private static HashMap<String, Point> getPointHashMap(Future<List<Point>> list) throws ExecutionException, InterruptedException {
        return list.get().stream()
                .collect(Collectors.toMap(Point::getMacAddress,
                        entry -> entry,
                        (prev, next) -> next,
                        HashMap::new));
    }

    public static void replaceAllPointsInLocalDatabase(Context context, List<Point> newPoints) {
        PointDao pointDao = LocalDatabase.getInstance(context).pointDao();
        Executors.newSingleThreadExecutor().execute(() -> {
            pointDao.deleteAll();
            pointDao.insertAll(newPoints);
            PreferencesService.setLocalBaseUpdateTimeStamp(context);
        });
    }

}

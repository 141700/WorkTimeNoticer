package ru.massandrashop.worktimenoticer.service.db;

import android.content.Context;
import ru.massandrashop.worktimenoticer.R;
import ru.massandrashop.worktimenoticer.model.Point;
import ru.massandrashop.worktimenoticer.utils.DataJsonConverter;
import ru.massandrashop.worktimenoticer.service.PreferencesService;
import ru.massandrashop.worktimenoticer.service.ui.ToastDemonstrator;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.*;

public class PointUpdater {

    public static void updateLocalPointDatabase(Context context) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                List<Point> points = DataJsonConverter.readJsonStream(PointUpdater.getPointsFromServer(context));
                if (points.size() > 0) {
                    PointLocalDatabaseService.replaceAllPointsInLocalDatabase(context, points);
                    ToastDemonstrator.showToast(context, R.string.toast_update_ok);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public static InputStream getPointsFromServer(Context context) throws IOException {
        URL url = new URL(PreferencesService.getUrlForGetAllPoints(context)); // TODO android:usesCleartextTraffic="true"  -- use HTTPS
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            return urlConnection.getInputStream();
        } finally {
            urlConnection.disconnect();
        }
    }

}

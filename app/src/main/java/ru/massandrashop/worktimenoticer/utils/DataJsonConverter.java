package ru.massandrashop.worktimenoticer.utils;

import android.util.JsonReader;
import ru.massandrashop.worktimenoticer.model.IdType;
import ru.massandrashop.worktimenoticer.model.Point;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataJsonConverter {

    public static List<Point> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readPointArray(reader);
        } finally {
            reader.close();
        }
    }

    public static List<Point> readPointArray(JsonReader reader) throws IOException {
        List<Point> points = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            points.add(readPoint(reader));
        }
        reader.endArray();
        return points;
    }

    public static Point readPoint(JsonReader reader) throws IOException {
        String title = null;
        String macAddress = null;
        String officeTitle = null;
        IdType type = null;
        reader.beginObject();
        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "title":
                    title = reader.nextString();
                    break;
                case "macAddress":
                    macAddress = reader.nextString();
                    break;
                case "officeTitle":
                    officeTitle = reader.nextString();
                    break;
                case "type":
                    type = IdType.valueOf(reader.nextString());
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Point(title, macAddress, officeTitle, type);
    }
}

package ru.massandrashop.worktimenoticer.utils;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ProvidedTypeConverter
public class LocalDateTimeConverter {

    @TypeConverter
    public static LocalDateTime toDate(Long dateLong) {
        return dateLong == null ? null
                : LocalDateTime.ofInstant(Instant.ofEpochMilli(dateLong), ZoneId.systemDefault());
    }

    @TypeConverter
    public static Long fromDate(LocalDateTime date) {
        return date == null ? null
                : ZonedDateTime.of(date, ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

}

package ru.massandrashop.worktimenoticer.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import org.jetbrains.annotations.NotNull;

@Entity
public class Point {
    @NonNull
    private final String title;

    @PrimaryKey
    @NonNull
    private final String macAddress;

    @NonNull
    private final String officeTitle;

    @NonNull
    private final IdType type;

    public Point(@NotNull String title, @NotNull String macAddress, @NotNull String officeTitle, @NotNull IdType type) {
        this.title = title;
        this.macAddress = macAddress.toUpperCase();
        this.officeTitle = officeTitle;
        this.type = type;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    @NotNull
    public String getMacAddress() {
        return macAddress;
    }

    @NotNull
    public String getOfficeTitle() {
        return officeTitle;
    }

    @NotNull
    public IdType getType() {
        return type;
    }

}

package ru.massandrashop.worktimenoticer.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity
public class Record {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private final String name;

    private final String macAddress;

    private LocalDateTime timeStamp;

    public Record(String name, String macAddress) {
        this.name = name;
        this.macAddress = macAddress.toUpperCase();
        this.timeStamp = LocalDateTime.now();
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTimeStamp(LocalDateTime ldt) {
        this.timeStamp = ldt;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

}

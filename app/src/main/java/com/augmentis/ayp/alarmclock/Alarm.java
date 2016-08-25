package com.augmentis.ayp.alarmclock;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Noppharat on 8/24/2016.
 */
public class Alarm {
    private UUID id;
    private Date time;
    boolean isActive;

    public Alarm(){
        this(UUID.randomUUID());
    }

    public Alarm(UUID uuid){
        id = uuid;
        time = new Date();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getTimeInString(Date inputTime){
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh : mm a");
        return dateFormat.format(inputTime).toString();
    }
}

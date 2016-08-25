package com.augmentis.ayp.alarmclock;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import com.augmentis.ayp.alarmclock.AlarmDBSchema.AlarmTable;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Noppharat on 8/24/2016.
 */
public class AlarmCursorWrapper extends CursorWrapper {
    private static final String TAG = "AlarmCursorWrapper";

    public AlarmCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Alarm getAlarm() {
        String uuid = getString(getColumnIndex(AlarmTable.Cols.UUID));
        Log.d(TAG, "getAlarm: STRING TIME --> " +getString(getColumnIndex(AlarmTable.Cols.TIME)));
        long time = getLong(getColumnIndex(AlarmTable.Cols.TIME));
        int isActive = getInt(getColumnIndex(AlarmTable.Cols.ISACTIVE));
        Log.d(TAG, "getAlarm: time " + time);
        Alarm alarm = new Alarm(UUID.fromString(uuid));
        Log.d(TAG, "getAlarm: new " + new Date(time));
        alarm.setTime(new Time(time));
        alarm.setActive(isActive != 0);

        return alarm;
    }
}

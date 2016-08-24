package com.augmentis.ayp.alarmclock;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.augmentis.ayp.alarmclock.AlarmDBSchema.AlarmTable;

import java.sql.Time;
import java.util.UUID;

/**
 * Created by Noppharat on 8/24/2016.
 */
public class AlarmCursorWrapper extends CursorWrapper {

    public AlarmCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Alarm getAlarm(){
        String uuid = getString(getColumnIndex(AlarmTable.Cols.UUID));
        long time = getLong(getColumnIndex(AlarmTable.Cols.TIME));
        int isActive = getInt(getColumnIndex(AlarmTable.Cols.ISACTIVE));

        Alarm alarm = new Alarm(UUID.fromString(uuid));
        alarm.setTime(new Time(time));
        alarm.setActive(isActive != 0);

        return alarm;
    }
}

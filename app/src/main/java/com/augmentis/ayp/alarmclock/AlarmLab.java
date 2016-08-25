package com.augmentis.ayp.alarmclock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.augmentis.ayp.alarmclock.AlarmDBSchema.AlarmTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Noppharat on 8/24/2016.
 */
public class AlarmLab {
    private static final String TAG = "AlarmLab";

    private static AlarmLab instance;

    public static AlarmLab getInstance(Context context) {
        if (instance == null) {
            instance = new AlarmLab(context);
        }
        return instance;
    }

    public static ContentValues getContentValures(Alarm alarm) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AlarmTable.Cols.UUID, alarm.getId().toString());
        Log.d(TAG, "getContentValures: " + alarm.getTime().toString());
        contentValues.put(AlarmTable.Cols.TIME, alarm.getTime().getTime());
        contentValues.put(AlarmTable.Cols.ISACTIVE, alarm.getTime().toString());
        return contentValues;
    }

    private Context _context;
    private SQLiteDatabase _database;

    public AlarmLab(Context context) {
        _context = context;
        AlarmBaseHelper alarmBaseHelper = new AlarmBaseHelper(context);
        _database = alarmBaseHelper.getWritableDatabase();
    }

    public Alarm getAlarmById(UUID uuid) {
        AlarmCursorWrapper cursor = queryAlarms(AlarmTable.Cols.UUID + " = ? ",
                new String[]{uuid.toString()});
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            Log.d(TAG, "getAlarmById: time " + cursor.getAlarm().getTime() + " <---");
            return cursor.getAlarm();
        } finally {
            cursor.close();
        }
    }

    public AlarmCursorWrapper queryAlarms(String whereClause, String[] whereArgs) {
        Cursor cursor = _database.query(AlarmTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);
        return new AlarmCursorWrapper(cursor);
    }

    public List<Alarm> getAlarm() {
        List<Alarm> alarms = new ArrayList<>();
        AlarmCursorWrapper cursor = queryAlarms(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                alarms.add(cursor.getAlarm());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return alarms;
    }

    public void addAlarm(Alarm alarm) {
        ContentValues contentValues = getContentValures(alarm);
        _database.insert(AlarmTable.NAME, null, contentValues);
    }

    public void deleteAlarm(UUID uuid) {
        _database.delete(AlarmTable.NAME,
                AlarmTable.Cols.UUID + " = ? ",
                new String[]{uuid.toString()});
    }

    public void updateAlarm(Alarm alarm) {
        Log.d(TAG, "updateAlarm: update ");
        String uuid = alarm.getId().toString();
        ContentValues contentValues = getContentValures(alarm);
        _database.update(AlarmTable.NAME, contentValues, AlarmTable.Cols.UUID + " = ? ",
                new String[]{uuid.toString()});
    }
}

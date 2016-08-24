package com.augmentis.ayp.alarmclock;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.augmentis.ayp.alarmclock.AlarmDBSchema.AlarmTable;

/**
 * Created by Noppharat on 8/24/2016.
 */
public class AlarmBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "alarmBase.db";
    private static final String TAG = "AlarmBaseHelper";

    public AlarmBaseHelper(Context context){
        super(context,DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + AlarmTable.NAME
                + "("
                +"_id integer primary key autoincrement, "
                + AlarmTable.Cols.UUID + ","
                + AlarmTable.Cols.TIME + ","
                + AlarmTable.Cols.ISACTIVE + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

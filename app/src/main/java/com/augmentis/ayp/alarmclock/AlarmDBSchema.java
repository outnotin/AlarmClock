package com.augmentis.ayp.alarmclock;

/**
 * Created by Noppharat on 8/24/2016.
 */
public class AlarmDBSchema {
    public static final class AlarmTable{
        public static final String NAME = "alarms";
        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TIME = "time";
            public static final String ISACTIVE = "isactive";
        }
    }
}


package com.augmentis.ayp.alarmclock;


import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by Noppharat on 8/24/2016.
 */
public class AlarmListActivity extends SingleFragmentActivity{

    @Override
    protected Fragment onCreateFragment() {
        Log.d("ALARMLIST", "AlarmListActivity access");
        return new AlarmListFragment();
    }


}

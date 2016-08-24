package com.augmentis.ayp.alarmclock;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.UUID;

public class AlarmActivity extends SingleFragmentActivity {

    protected static final String ALARM_ID = "AlarmActivity.AlarmId";


    public static Intent newIntent(Context context, UUID id){
        Intent intent = new Intent(context, AlarmActivity.class);
        intent.putExtra(ALARM_ID, id);
        return intent;
    }

    @Override
    protected Fragment onCreateFragment() {
        UUID alarmId = (UUID) getIntent().getSerializableExtra(ALARM_ID);
        Fragment fragment = AlarmFragment.newInstance(alarmId);
        return fragment;
    }
}

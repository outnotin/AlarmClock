package com.augmentis.ayp.alarmclock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Noppharat on 8/24/2016.
 */
public class AlarmFragment extends Fragment {

    private static final String ALARM_ID = "AlarmFragment.ALARM_ID";
    private static final String DIALOG_TIME = "AlarmFragment.DIALOG_TIME";
    private static final int REQUEST_TIME = 2321;


    private TextView _alarmTextView;
    private Button _setAlarmButton;
    private Button _saveAlarmButton;
    private Button _cancelAlarmButton;

    private boolean isNewAlarm;

    private Alarm _alarm;

    public static AlarmFragment newInstance(UUID alarmId){
        Bundle args = new Bundle();
        args.putSerializable(ALARM_ID, alarmId);
        AlarmFragment alarmFragment = new AlarmFragment();
        alarmFragment.setArguments(args);
        return alarmFragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }

        if(requestCode == REQUEST_TIME){
            Date time = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            Log.d("Activity result", "Time : " + time);
            Log.d("Activity result", "is new : " + isNewAlarm);
            _alarmTextView.setText(_alarm.getTimeInString(time));
            _alarm.setTime(time);

        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isNewAlarm = false;
        UUID uuid = (UUID) getArguments().getSerializable(ALARM_ID);
        _alarm = AlarmLab.getInstance(getActivity()).getAlarmById(uuid);
        if (_alarm == null) {
            _alarm = new Alarm();
            isNewAlarm = true;
        }
        Log.d("On create alarm ", _alarm.getTime().toString());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alarm, container, false);

        _alarmTextView = (TextView) v.findViewById(R.id.alarm_text_view);



        _alarmTextView.setText(_alarm.getTimeInString(_alarm.getTime()));

        _setAlarmButton = (Button) v.findViewById(R.id.set_alarm);
        _setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(_alarm.getTime());
                timePickerFragment.setTargetFragment(AlarmFragment.this, REQUEST_TIME);
                timePickerFragment.show(fm, DIALOG_TIME);
            }
        });

        _saveAlarmButton = (Button) v.findViewById(R.id.save_alarm);
        _saveAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("alarm" , "-0-0-0-0- > " + _alarm.getTime().toString());

                //check if id is already in AlarmLab
                //if id is not in AlarmLab
                //

                //if id is already exist
                //

                if(isNewAlarm){
                    AlarmLab.getInstance(getActivity()).addAlarm(_alarm);
                }else{
                    AlarmLab.getInstance(getActivity()).updateAlarm(_alarm);
                }

                Intent intent = new Intent(getContext(), AlarmListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        _cancelAlarmButton = (Button) v.findViewById(R.id.cancel_alarm);
        _cancelAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AlarmListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        return v;
    }

}

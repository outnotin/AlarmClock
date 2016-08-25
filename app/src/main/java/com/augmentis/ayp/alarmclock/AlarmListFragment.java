package com.augmentis.ayp.alarmclock;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Noppharat on 8/24/2016.
 */
public class AlarmListFragment extends Fragment{

    private static final int REQUEST_UPDATE_ALARM = 1111;
    private static final int REQUEST_DELETE = 2313;
    private static final String DIALOG_DELETE = "AlarmList.DIALOG_DELETE";

    private RecyclerView _alarmRecyclerView;
    private AlarmAdapter _adapter;
    private Alarm _alarm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Log.d("ALARMLIST", "----------------------------ALARMLIST-----------------------------------");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode != Activity.RESULT_OK){
            return;
        }

        if(requestCode == REQUEST_DELETE){
            updateUI();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alarm_list, container, false);
        _alarmRecyclerView = (RecyclerView) v.findViewById(R.id.alarm_recycler_view);
        _alarmRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.alarm_list_menu, menu);
        //MenuItem menuItem = menu.findItem(R.id.menu_item_new_alarm);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_new_alarm:
                Alarm alarm = new Alarm();
                _alarm = alarm;
                Intent intent = AlarmActivity.newIntent(getActivity(), _alarm.getId());
                startActivityForResult(intent, REQUEST_UPDATE_ALARM);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


    public void updateUI(){
        AlarmLab alarmLab = AlarmLab.getInstance(getActivity());
        List<Alarm> alarms = alarmLab.getAlarm();
        if(_adapter == null){
            _adapter = new AlarmAdapter(alarms, this);
            _alarmRecyclerView.setAdapter(_adapter);
        }else{
            _adapter.setAlarms(alarmLab.getAlarm());
            _adapter.notifyDataSetChanged();
        }
    }

    private class AlarmHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public TextView _timeTextView;
        public Switch _isActive;

        Alarm _alarm;
        int _position;

        public AlarmHolder(View itemView) {
            super(itemView);

            _timeTextView = (TextView) itemView.findViewById(R.id.list_item_clock);
            _isActive = (Switch) itemView.findViewById(R.id.list_item_switch);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void bind(final Alarm alarm, int position){
            _alarm = alarm;
            _timeTextView.setText(_alarm.getTimeInString(_alarm.getTime()));
//            _timeTextView.setText(_alarm.getTime().toString());
            Log.d("ALARMLIST", "-------->" + _alarm.getTimeInString(_alarm.getTime()));
            _isActive.setChecked(_alarm.isActive());
            _isActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    _alarm.setActive(isChecked);
                    AlarmLab.getInstance(getActivity()).updateAlarm(_alarm);
                }
            });
            _position = position;
        }

        @Override
        public void onClick(View v) {
            Intent intent = AlarmActivity.newIntent(getActivity(), _alarm.getId());
            startActivityForResult(intent, REQUEST_UPDATE_ALARM);
        }

        @Override
        public boolean onLongClick(View v) {
            FragmentManager fm = getFragmentManager();
            DeleteDialog deleteDialog = DeleteDialog.newInstance(_alarm.getId());

            deleteDialog.setTargetFragment(AlarmListFragment.this, REQUEST_DELETE);
            deleteDialog.show(fm, DIALOG_DELETE);
            return true;
        }
    }

    private class AlarmAdapter extends RecyclerView.Adapter<AlarmHolder>{
        private List<Alarm> _alarms;
        private Fragment _fragment;

        public AlarmAdapter(List<Alarm> alarms, Fragment fragment){
            _alarms = alarms;
            _fragment = fragment;
        }

        public void setAlarms(List<Alarm> alarms){
            _alarms = alarms;
        }

        @Override
        public AlarmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View v = layoutInflater.inflate(R.layout.list_item_alarm, parent, false);

            return new AlarmHolder(v);
        }

        @Override
        public void onBindViewHolder(AlarmHolder holder, int position) {
            Alarm alarm = _alarms.get(position);
            holder.bind(alarm, position);
        }

        @Override
        public int getItemCount() {
            return _alarms.size();
        }
    }


}

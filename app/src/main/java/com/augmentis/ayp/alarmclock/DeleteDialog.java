package com.augmentis.ayp.alarmclock;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.UUID;

/**
 * Created by Noppharat on 8/24/2016.
 */
public class DeleteDialog extends DialogFragment {

    public static DeleteDialog newInstance(UUID alarmId){
        DeleteDialog deleteDialog = new DeleteDialog();
        Bundle args = new Bundle();
        args.putSerializable("ARG_ALARM", alarmId);
        deleteDialog.setArguments(args);
        return deleteDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final UUID alarmId = (UUID) getArguments().getSerializable("ARG_ALARM");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_delete)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlarmLab.getInstance(getActivity()).deleteAlarm(alarmId);
                        getTargetFragment().onResume();
                        //sendResult(Activity.RESULT_OK);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }

//    private void sendResult(int resultOk){
//        if(getTargetFragment() == null){
//            return;
//        }
//        getTargetFragment().onActivityResult(getTargetRequestCode(), resultOk, null);
//    }
}

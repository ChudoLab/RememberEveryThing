package com.chudolab.remembereverything.options;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;

/**
 * Created by Chudo on 17.11.2015.
 */
public class TimePickerFragment extends DialogFragment {
    TimePickerDialog.OnTimeSetListener onTimeSet;

    private int hours;
    private int minutes;

    public TimePickerFragment() {
    }

    public void setCallBack(TimePickerDialog.OnTimeSetListener onTime) {
        onTimeSet = onTime;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        hours = args.getInt("hours");
        minutes = args.getInt("minutes");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(), onTimeSet, hours, minutes, true);
    }
}

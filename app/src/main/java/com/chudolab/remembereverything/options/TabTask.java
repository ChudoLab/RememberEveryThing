package com.chudolab.remembereverything.options;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.chudolab.remembereverything.R;

import java.util.Calendar;

/**
 * Created by Chudo on 13.11.2015.
 */
public class TabTask extends Fragment {
//    private DatePicker pickerDate;
//    private TextView showDate;
//    private TextView showTime;
//    private TimePicker timePicker;
    private Calendar calendar;
    final static int RESULT_TAB_TASK = 3;
    View v;
//    static final int DATE_PICKER_ID = 1111;
//    private int year;
//    private int month;
//    private int day;
    private int[] gotTime;
    private int[] gotDate;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tab_task, container, false);
        // yes buttons
        Button reminderButton = (Button) v.findViewById(R.id.butReminder);
        Button calendarButton = (Button) v.findViewById(R.id.butCalendar);

        reminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();

            }
        });
        //ok button
        Button okAndGo = (Button) v.findViewById(R.id.buttonOkAndGo);
        okAndGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (gotTime != null) {
                    intent.putExtra("time", gotTime);
                }
                if (gotDate != null) {
                    intent.putExtra("date", gotDate);
                }
                getActivity().setResult(RESULT_TAB_TASK, intent);
                getActivity().setIntent(intent);
                getActivity().finish();
            }
        });
        return v;
    }

    private void showTimePicker() {
        TimePickerFragment time = new TimePickerFragment();

        calendar = Calendar.getInstance();
        Bundle args = new Bundle();
        args.getInt("hour", calendar.get(Calendar.HOUR));
        args.getInt("minute", calendar.get(Calendar.MINUTE));
        time.setArguments(args);
        //TODO does not work current time
//        timePicker.setHour(calendar.get(Calendar.HOUR));
//        timePicker.setMinute(calendar.get(Calendar.MINUTE));
        time.setCallBack(onTime);
        time.show(getActivity().getFragmentManager(), "Time Picker");
    }

    TimePickerDialog.OnTimeSetListener onTime = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            TextView showTime = (TextView) v.findViewById(R.id.timeShow);
            showTime.setText("You've set reminder on " + hourOfDay + " hour " + " and " + minute + "minutes");
            showTime.setVisibility(View.VISIBLE);

            gotTime = new int[]{hourOfDay, minute};

        }
    };

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();

        calendar = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calendar.get(Calendar.YEAR));
        args.putInt("month", calendar.get(Calendar.MONTH));
        args.putInt("day", calendar.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);

        date.setCallBack(onDate);
        date.show(getActivity().getSupportFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            TextView showTime = (TextView) v.findViewById(R.id.dateShow);
            showTime.setText("You've set calendar on " + "year " + year + " year " + monthOfYear + "month " + " and " + dayOfMonth + " day");
            showTime.setVisibility(View.VISIBLE);
            gotDate = new int[]{dayOfMonth, monthOfYear, year};
        }
    };

}


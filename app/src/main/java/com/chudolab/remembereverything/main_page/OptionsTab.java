package com.chudolab.remembereverything.main_page;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;


import com.chudolab.remembereverything.CurrentTodoAdapter;
import com.chudolab.remembereverything.R;
import com.chudolab.remembereverything.Singleton;
import com.chudolab.remembereverything.options.DatePickerFragment;
import com.chudolab.remembereverything.options.TimePickerFragment;

import java.util.ArrayList;
import java.util.Calendar;

public class OptionsTab extends Fragment {
    ArrayList<String> existingTopics;
    private int[] gotTime;
    private int[] gotDate;
    private Calendar calendar;
    private TextView showTime;
    private TextView showDate;
    private ImageView reminderShow;
    private ImageView calendarShow;
    private Switch wantTodo;
    private Switch wantName;
    private Switch wantTopic;
    private Switch ifRemind;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.options_tab, container, false);

        wantTodo = (Switch) v.findViewById(R.id.wantTodo);
        reminderShow = (ImageView) getActivity().findViewById(R.id.reminderShow);
        calendarShow = (ImageView) getActivity().findViewById(R.id.calendarShow);
        Switch enableReminder = (Switch) v.findViewById(R.id.wantReminder);
        final Switch enableCalendar = (Switch) v.findViewById(R.id.wantCalendar);
        final Switch isTodayReminder = (Switch) v.findViewById(R.id.ifTodayReminder);
        wantName = (Switch) v.findViewById(R.id.wantName);
        existingTopics = Singleton.getInstance().getSubjects();
        wantTopic = (Switch) v.findViewById(R.id.wantTopic);
        ifRemind = (Switch) v.findViewById(R.id.ifRemind);

        //default
        showDate = (TextView) v.findViewById(R.id.dateShow);
        showTime = (TextView)v.findViewById(R.id.timeShow);
        calendar = Calendar.getInstance();
        gotDate = new int[]{calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.MONTH),
                calendar.get(Calendar.YEAR)};
        gotTime = new int[]{calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE)};
        showTime.setText("Time: " + gotTime[0] + ":" + gotTime[1]);
        showDate.setText("Date: " + gotDate[0] + "/" + gotDate[1] + "/" + gotDate[2]);

        //ENABLE TODOLIST

        wantTodo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EditText currentNoteText = (EditText) getActivity().findViewById(R.id.currentNoteText);
                ListView currentTodoNoteText = (ListView) getActivity().findViewById((R.id.currentTodoNoteText));
                if (isChecked) {
                    currentNoteText.setVisibility(View.GONE);
                    currentTodoNoteText.setVisibility(View.VISIBLE);
                } else {
                    currentNoteText.setVisibility(View.VISIBLE);
                    currentTodoNoteText.setVisibility(View.GONE);

                }
            }
        });
        //ENABLE NAME


        wantName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getActivity().findViewById(R.id.addName).setVisibility(View.VISIBLE);
                } else {
                    getActivity().findViewById(R.id.addName).setVisibility(View.GONE);
                }
            }
        });

        //TOPICS

        ArrayAdapter<String> topicAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.support_simple_spinner_dropdown_item, existingTopics);
        topicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) getActivity().findViewById(R.id.existingTopics);
        spinner.setAdapter(topicAdapter);

//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//
//        });

        wantTopic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getActivity().findViewById(R.id.addTopic).setVisibility(View.VISIBLE);
                    getActivity().findViewById(R.id.existingTopics).setVisibility(View.VISIBLE);
                } else {
                    getActivity().findViewById(R.id.addTopic).setVisibility(View.GONE);
                    getActivity().findViewById(R.id.existingTopics).setVisibility(View.GONE);
                }
            }
        });
//       REMINDER
        ifRemind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    v.findViewById(R.id.reminderLayout).setVisibility(View.VISIBLE);
                    isTodayReminder.setChecked(true);
                    ifRemind.setChecked(true);
                    enableCalendar.setChecked(false);
                    reminderShow.setVisibility(View.VISIBLE);


                } else {
                    v.findViewById(R.id.reminderLayout).setVisibility(View.GONE);
                    reminderShow.setVisibility(View.INVISIBLE);
                    calendarShow.setVisibility(View.INVISIBLE);
                }


            }
        });
        //DATE AND TIME PICKERS

        isTodayReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Button setDate = (Button) v.findViewById(R.id.setDate);
                if (!isChecked) {
                    showDatePicker();
                    showDate.setVisibility(View.VISIBLE);
                    setDate.setVisibility(View.VISIBLE);
                } else {
                    setDate.setVisibility(View.GONE);
                    showDate.setVisibility(View.GONE);
                    showDate.setText("");
                    calendarShow.setVisibility(View.GONE);
                    gotDate = null;
                }
            }
        });

        enableReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ImageView imageRemider = (ImageView) v.findViewById(R.id.imageReminder);
                if (isChecked) {
                    imageRemider.setVisibility(View.VISIBLE);
                    reminderShow.setVisibility(View.VISIBLE);
                } else {
                    imageRemider.setVisibility(View.GONE);
                    reminderShow.setVisibility(View.GONE);
                }
            }
        });

        enableCalendar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ImageView imageCalendar = (ImageView) v.findViewById(R.id.imageCalendar);

                if (isChecked) {
                    imageCalendar.setVisibility(View.VISIBLE);
                    calendarShow.setVisibility(View.VISIBLE);
                } else {

                    imageCalendar.setVisibility(View.GONE);
                    calendarShow.setVisibility(View.GONE);
                }
            }
        });
        Button setTime = (Button) v.findViewById(R.id.setTime);
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });
        Button setDate = (Button) v.findViewById(R.id.setDate);
        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        return v;
    }

    private void showTimePicker() {
        TimePickerFragment time = new TimePickerFragment();

        Bundle args = new Bundle();
        args.putInt("hours", calendar.get(Calendar.HOUR));
        args.putInt("minutes", calendar.get(Calendar.MINUTE));
        time.setArguments(args);

        time.setCallBack(onTime);
        time.show(getActivity().getFragmentManager(), "Time Picker");
    }

    TimePickerDialog.OnTimeSetListener onTime = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            showTime = (TextView) getActivity().findViewById(R.id.timeShow);
            showTime.setText("Time: " + hourOfDay + ":" + minute);
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

            showDate.setText("Date: " + dayOfMonth + "/" + monthOfYear + "/" + year);
            showDate.setVisibility(View.VISIBLE);
            gotDate = new int[]{dayOfMonth, monthOfYear, year};
        }
    };

//    public interface OnDateSetListener {
//        public void onDateSet(int[] gotDate);
//    }

}


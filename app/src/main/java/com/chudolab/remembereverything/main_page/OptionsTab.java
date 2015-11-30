package com.chudolab.remembereverything.main_page;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
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
    private TextView reminderShow;
    private TextView calendarShow;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.options_tab, container, false);

        //ENABLE TODOLIST

        Switch wantTodo = (Switch)v.findViewById(R.id.wantTodo);
        wantTodo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EditText currentNoteText = (EditText)getActivity().findViewById(R.id.currentNoteText);
                ListView currentTodoNoteText = (ListView)getActivity().findViewById((R.id.currentTodoNoteText));
                if(isChecked){
                    currentNoteText.setVisibility(View.GONE);
                    currentTodoNoteText.setVisibility(View.VISIBLE);
                }
                else{
                    currentNoteText.setVisibility(View.VISIBLE);
                    currentTodoNoteText.setVisibility(View.GONE);

                }
            }
        });
        //ENABLE NAME

        Switch wantName = (Switch)v.findViewById(R.id.wantName);
        wantName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    getActivity().findViewById(R.id.addName).setVisibility(View.VISIBLE);
                }
                else {
                    getActivity().findViewById(R.id.addName).setVisibility(View.GONE);
                }
            }
        });

        //TOPICS

        existingTopics = Singleton.getInstance().getSubjects();

        ArrayAdapter<String> topicAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, existingTopics);
        topicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) getActivity().findViewById(R.id.existingTopics);
        spinner.setAdapter(topicAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //TODO put it in current note state
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //TODO make a defoult topic
            }

        });
        Switch wantTopic = (Switch) v.findViewById(R.id.wantTopic);
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
        //DATE AND TIME PICKERS
        reminderShow = (TextView) getActivity().findViewById(R.id.reminderShow);
        calendarShow = (TextView) getActivity().findViewById(R.id.calendarShow);
        Switch enableReminder = (Switch) v.findViewById(R.id.wantReminder);
        Switch enableCalendar = (Switch) v.findViewById(R.id.wantCalendar);

        enableReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showTimePicker();
                } else {
                    showTime = (TextView) getActivity().findViewById(R.id.timeShow);
                    showTime.setVisibility(View.GONE);
                    showTime.setText("");
                    reminderShow.setVisibility(View.GONE);
                    gotTime = null;
                }
            }
        });
        enableCalendar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showDatePicker();
                } else {
                    showDate = (TextView) getActivity().findViewById(R.id.dateShow);
                    showDate.setVisibility(View.GONE);
                    showDate.setText("");
                    calendarShow.setVisibility(View.GONE);
                    gotDate = null;

                }
            }
        });


        return v;
    }

    private void showTimePicker() {
        TimePickerFragment time = new TimePickerFragment();

        calendar = Calendar.getInstance();
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
            showTime.setText("You've set reminder on " + hourOfDay +
                    " hour " + " and " + minute + "minutes");
            showTime.setVisibility(View.VISIBLE);


            reminderShow.setVisibility(View.VISIBLE);
            reminderShow.setText("You've set reminder on " + hourOfDay +
                    " hour " + " and " + minute + "minutes");

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
            showDate = (TextView) getActivity().findViewById(R.id.dateShow);
            showDate.setText("You've set calendar on " + "year " + year +
                    " year " + monthOfYear + "month " + " and " + dayOfMonth + " day");
            showDate.setVisibility(View.VISIBLE);
            gotDate = new int[]{dayOfMonth, monthOfYear, year};


            calendarShow.setVisibility(View.VISIBLE);
            calendarShow.setText("You've set calendar on " + "year " + year +
                    " year " + monthOfYear + "month " + " and " + dayOfMonth + " day");
        }
    };
}

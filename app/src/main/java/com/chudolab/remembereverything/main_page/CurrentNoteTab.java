package com.chudolab.remembereverything.main_page;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.chudolab.remembereverything.CurrentTodoAdapter;
import com.chudolab.remembereverything.R;
import com.chudolab.remembereverything.Singleton;
import com.chudolab.remembereverything.options.AlarmReceiver;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class CurrentNoteTab extends Fragment {
    private CurrentTodoAdapter toDoAdapter;
    private ArrayList<CheckBox> listOfDoing;
    private ArrayList<String> existingTopics;
    private ArrayAdapter<String> topicAdapter;
    private ParseObject po;
    private View v;
    private Spinner spinnerTopics;
    private String spinnerRes;
    private EditText addName;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.current_note_create_tab, container, false);

        listOfDoing = new ArrayList<CheckBox>();
        ListView currentTodoList = (ListView) v.findViewById(R.id.currentTodoNoteText);
        toDoAdapter = new CurrentTodoAdapter(getActivity().getApplicationContext(),
                listOfDoing, this);
        currentTodoList.setAdapter(toDoAdapter);

        existingTopics = Singleton.getInstance().getSubjects();
        topicAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.support_simple_spinner_dropdown_item, existingTopics);
        topicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTopics = (Spinner) v.findViewById(R.id.existingTopics);
        spinnerTopics.setAdapter(topicAdapter);

        Button saveClose = (Button) v.findViewById(R.id.buttonSaveClose);
        saveClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClick();
            }
        });

        return v;
    }

    public void onSaveButtonClick() {
        EditText currentNoteText = (EditText) getActivity().findViewById(R.id.currentNoteText);
        final Switch ifTodo = (Switch) getActivity().findViewById(R.id.wantTodo);
        Switch ifRemind = (Switch) getActivity().findViewById(R.id.ifRemind);
        Switch wantName = (Switch) getActivity().findViewById(R.id.wantName);
        Switch wantTopic = (Switch) getActivity().findViewById(R.id.wantTopic);
        EditText addTopic = (EditText) getActivity().findViewById(R.id.addTopic);
        addName = (EditText) getActivity().findViewById(R.id.addName);
        String gotTopic = addTopic.getText().toString();
        final String resCurrentNote = currentNoteText.getText().toString();

        if (!resCurrentNote.isEmpty() || (toDoAdapter.getListOfTodo().size() > 0)) { //if note text is not empty
            if (!ifRemind.isChecked()) {
                if (!ifTodo.isChecked()) {//THIS IS SIMPLE NOTE
                    po = new ParseObject("SimpleNotes");
                    saveAsSimple(po, resCurrentNote);

                } else if (ifTodo.isChecked() && (toDoAdapter.getListOfTodo().size() > 0)) {//THIS IS TO DO
                    po = new ParseObject("ToDoNotes");
                    saveAsTodo(po);
                }
                if (wantName.isChecked()) {
                    po.put("name", gotName(resCurrentNote));
                }
                if (wantTopic.isChecked()) { //topic checked
                    po.put("subject", getTopic(gotTopic));
                }
                po.put("user", ParseUser.getCurrentUser());
                po.saveInBackground();
                getActivity().finish();
            } else {  //TASK NOTE
                po = new ParseObject("TaskNotes");

                if (wantName.isChecked()) {
                    po.put("name", gotName(resCurrentNote));
                }
                if (wantTopic.isChecked()) { //topic checked
                    po.put("subject", getTopic(gotTopic));
                }
                if (!ifTodo.isChecked()) {
                    po.put("text", resCurrentNote);
                } else {
                    saveAsTodo(po);
                }
                po.put("user", ParseUser.getCurrentUser());
                po.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getContext(), "Saved ", Toast.LENGTH_SHORT).show();
                            String currentNoteId = po.getObjectId();
                            Log.e("current Id", currentNoteId);

                            if (!resCurrentNote.isEmpty()) {
                                saveAsTask(resCurrentNote, currentNoteId);
                            } else if (ifTodo.isChecked()) {
                                ArrayList<CheckBox> temp = toDoAdapter.getListOfTodo();
                                String todoInLine = new String();
                                for (int i = 0; i < temp.size(); i++) {
                                    todoInLine += (temp.get(i).getText().toString());
                                    todoInLine += (" ");
                                }
                                saveAsTask(todoInLine, currentNoteId);
                            }

                        } else {
                            Toast.makeText(getContext(), "Error ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } else Toast.makeText(getActivity(), "Note is empty", Toast.LENGTH_SHORT).show();
    }


    public ParseObject saveAsSimple(ParseObject po, String resCurrentNote) {

        po.put("text", resCurrentNote);


        return po;
    }

    public ParseObject saveAsTodo(ParseObject po) {


        ArrayList<CheckBox> temp = toDoAdapter.getListOfTodo();
        Boolean[] bools = new Boolean[temp.size()];
        String[] strings = new String[temp.size()];

        for (int i = 0; i < temp.size(); i++) {
            bools[i] = temp.get(i).isChecked();
            strings[i] = temp.get(i).getText().toString();
        }
        po.put("doingBoolean", Arrays.asList(bools));
        po.put("doing", Arrays.asList(strings));

        return po;
    }

    public void saveAsTask(String description, String currentNoteID) {

        Switch ifReminder = (Switch) getActivity().findViewById(R.id.wantReminder);
        Switch ifCalendar = (Switch) getActivity().findViewById(R.id.wantCalendar);

        TextView tv = (TextView) getActivity().findViewById(R.id.dateShow);
        String date = tv.getText().toString();
        TextView tv2 = (TextView) getActivity().findViewById(R.id.timeShow);
        String time = tv2.getText().toString();
        date = date.substring(6);
        String[] dateArray = date.split("/");
        time = time.substring(6);
        String[] timeArray = time.split(":");
        int hour = Integer.parseInt(timeArray[0]);
        int minute = Integer.parseInt(timeArray[1]);
        int year = Integer.parseInt(dateArray[2]);
        int month = Integer.parseInt(dateArray[1]);
        int day = Integer.parseInt(dateArray[0]);

        String name;
        if (!addName.getText().toString().isEmpty()) {
            name = addName.getText().toString();
        } else name = "The note";

        if (ifReminder.isChecked()) {

            setNotification(hour, minute, year, month, day, description, currentNoteID);
        }
        if (ifCalendar.isChecked()) {

            sendToCalendar(name, hour, minute, year, month, day, description);
        }
    }

    public String getTopic(String gotTopic) {

        if (!gotTopic.isEmpty()) { // added topic
            if (existingTopics.size() != 0) { //looking for same topic
                for (int i = 0; i < existingTopics.size(); i++) {
                    if (!existingTopics.get(i).equals(gotTopic)) {
                        existingTopics.add(gotTopic);
                        Singleton.getInstance().setSubjects(existingTopics);
                        topicAdapter.notifyDataSetChanged();
                    }
                }
            }
        } else { // getting from spinner
            spinnerRes = spinnerTopics.getSelectedItem().toString();
            gotTopic = spinnerRes;
        }
        return gotTopic;
    }

    public void sendToCalendar(String name, int hour, int minute, int year, int month, int day, String description) {

//        long startMillis = 0;
//        long endMillis = 0;
//        startMillis = beginTime.getTimeInMillis();
//        endMillis = endTime.getTimeInMillis();
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(year, month, day, hour, minute);

        Calendar endTime = Calendar.getInstance();
        endTime.set(year, month, day, (hour + 1), minute);


        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setType("vnd.android.cursor.item/event")
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false)

                .putExtra(CalendarContract.Events.TITLE, name)
                .putExtra(CalendarContract.Events.DESCRIPTION, "" + description)
//                .putExtra(CalendarContract.Events.RRULE, "FREQ=DAILY;COUNT=1") //The recurrence rule for the event. Column name. Somehow it works
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                .putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
        startActivity(intent);
    }

    public void setNotification(int hour, int minute, int year, int month, int day, String currentNoteText, String currentNoteId) {

        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
//        int noteID = Integer.parseInt(currentNoteId);
        intent.putExtra("noteID",10);
        intent.putExtra("currentNoteText", currentNoteText);

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 3444, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), pendingIntent);

    }

    public String gotName(String resCurrentNote) {
        if (!addName.getText().toString().isEmpty()) { //name added
            String resNoteName = addName.getText().toString();
            return resNoteName;
        } else { //default name
            if (resCurrentNote.length() > 10) {
                String resNote = resCurrentNote.substring(0, 10);
                return resNote;
            } else return resCurrentNote;
        }
    }
}

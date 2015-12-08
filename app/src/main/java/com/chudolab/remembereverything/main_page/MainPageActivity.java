package com.chudolab.remembereverything.main_page;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.chudolab.remembereverything.DrawerAppCompatActivity;
import com.chudolab.remembereverything.R;
import com.chudolab.remembereverything.Singleton;
import com.chudolab.remembereverything.type_of_notes.Note;
import com.chudolab.remembereverything.type_of_notes.SimpleNote;
import com.chudolab.remembereverything.type_of_notes.TaskNote;
import com.chudolab.remembereverything.type_of_notes.ToDoNote;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import view.SlidingTabLayout;

public class MainPageActivity extends DrawerAppCompatActivity implements CurrentNoteTab.OnGetListOfTodo{
    private CharSequence Titles[] = {"Note", "Options"};
    private int Numboftabs = 2;
    //Dowloaded from parse
    ArrayList<Note> simplesList;
    ArrayList<Note> toDoList;
    ArrayList<Note> taskList;
    ArrayList<CheckBox> todoCheckbox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        SlidingTabLayout tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setViewPager(pager);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.rgb(146, 170, 201); //#6997D3
            }

            @Override
            public int getDividerColor(int position) {
                return 0;
            }
        });
        downloadNotes();
    }

    //TODO Do i need it?
    @Override
    protected void onStop() {
        super.onStop();

        DrawerLayout dr = (DrawerLayout) findViewById(R.id.drawerLayout);
        dr.closeDrawers();

    }

    @Override
    public Toolbar getToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        return myToolbar;
    }

    public int getToolbarMenu() {
        return R.menu.toolbar_menu;
    }

    @Override
    public View getContentView() {
        View contentView = getLayoutInflater().inflate(R.layout.activity_main_page, null);
        return contentView;
    }


    public void setNotification(int[] times, String currentNoteText, String currentNoteId) {
//
//        Intent intent = new Intent(this, AlarmReceiver.class);
//
//        intent.putExtra("noteID", currentNoteId);
//        intent.putExtra("currentNoteText", currentNoteText);
//
//        Calendar calendar = Calendar.getInstance();
//
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, times[0]);
//        calendar.set(Calendar.MINUTE, times[1]);
//        calendar.set(Calendar.SECOND, 0);
//
//
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 3444, intent, 0);
//
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC_WAKEUP,
//                calendar.getTimeInMillis(), pendingIntent);
//
//    }
//
//    public void sendToCalendar(View v) {
//        long startMillis = 0;
//        long endMillis = 0;
//        Calendar beginTime = Calendar.getInstance();
//        beginTime.set(2012, 9, 14, 7, 30);
//        startMillis = beginTime.getTimeInMillis();
//        Calendar endTime = Calendar.getInstance();
//        endTime.set(2012, 9, 14, 8, 45);
//        endMillis = endTime.getTimeInMillis();
//
//        Intent intent = new Intent(Intent.ACTION_INSERT)
//                .setType("vnd.android.cursor.item/event")
//                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
//                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
//                .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false)
//
//                .putExtra(CalendarContract.Events.TITLE, "My Awesome Event")
//                .putExtra(CalendarContract.Events.DESCRIPTION, "Heading out with friends to do something awesome.")
//                .putExtra(CalendarContract.Events.RRULE, "FREQ=DAILY;COUNT=1") //The recurrence rule for the event. Column name. Somehow it works
//                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
//                .putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
//        startActivity(intent);
//    }


    }

    public void onSaveButtonClick(View v) {
        EditText currentNoteText = (EditText) findViewById(R.id.currentNoteText);
        Switch ifTodo = (Switch) findViewById(R.id.wantTodo);
        Switch ifReminder = (Switch) findViewById(R.id.wantReminder);
        Switch ifCalendar = (Switch) findViewById(R.id.wantCalendar);
        Switch ifRemind = (Switch) findViewById(R.id.ifRemind);
        Switch wantName = (Switch)findViewById(R.id.wantName);
        Switch wantTopic = (Switch)findViewById(R.id.wantTopic);
        EditText gotTopic = (EditText)findViewById(R.id.addTopic);
        EditText gotName = (EditText)findViewById(R.id.addName);
        ParseObject po;

        Log.e("button","clicked");

        //THIS IS SIMPLE NOTE
        if (!ifTodo.isChecked() && !ifRemind.isChecked()) {

            String resCurrentNote = currentNoteText.getText().toString();
            if (!resCurrentNote.isEmpty()) { //if note text is not empty
                po = new ParseObject("SimpleNotes");
                if(wantName.isChecked() && !gotName.getText().toString().isEmpty()) { //name checked and exist
                    po.put("name", gotName.getText().toString());
                }
                if(wantTopic.isChecked() && !gotTopic.getText().toString().isEmpty()) { //topic checked and exist
                    po.put("subject", gotTopic.getText().toString());
                }
                po.put("text", resCurrentNote);
                Log.e("simple", "" + resCurrentNote);
                po.saveInBackground();

            }else Toast.makeText(MainPageActivity.this, "Note is empty!", Toast.LENGTH_SHORT).show();

            //THIS IS TO DO
        } else if (ifTodo.isChecked()) {
            //get arraylist fragment activ

            Log.e("size", "" + todoCheckbox.size());
            for (int i = 0; i <todoCheckbox.size() ; i++) {
                System.out.println(todoCheckbox.get(i).getText().toString());
            }

            //THIS IS TASK
        } else if (ifRemind.isChecked()) {


        }

//
//                if (noteType == RESULT_SIMPLE_NOTE) {
//
//                    if (!gotOptions.get(0).isEmpty()) {
//                        po.put("name", gotOptions.get(0));
//                    }
//                    if (!gotOptions.get(1).isEmpty()) {
//                        po.put("subject", gotOptions.get(1));
//                    }
//
//                } else if (noteType == RESULT_SIMPLE_TODO) {
//
//                }

//        if (v.getId() == R.id.buttonSaveClose) {
//
//
//
//
//
//                if (noteType == RESULT_TAB_TASK) {
//                    if (times != null) {
//
////                        po.put("text", resCurrentNote);
////
////                        po.saveInBackground(new SaveCallback() {
////                            @Override
////                            public void done(ParseException e) {
////                                if (e == null) {
////                                    Toast.makeText(getApplicationContext(), "Saved ", Toast.LENGTH_SHORT).show();
////                                    currentNoteId = po.getObjectId();
////                                    Log.e("current Id", currentNoteId);
////
////                                } else {
////                                    Toast.makeText(getApplicationContext(), "Error ", Toast.LENGTH_SHORT).show();
////                                }
////                            }
////                        });
//                        setNotification(times, currentNoteText.getText().toString(), currentNoteId);
//                        finish();
//                    }
//
//                }
//                //TODO default name and subject
//
//                po.put("text", resCurrentNote);
//
//                po.saveInBackground(new SaveCallback() {
//                    @Override
//                    public void done(ParseException e) {
//                        if (e == null) {
//                            Toast.makeText(getApplicationContext(), "Saved ", Toast.LENGTH_SHORT).show();
//                            finish();
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Error ", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            } else {
//                Toast.makeText(MainActivity.this, "Note is empty!", Toast.LENGTH_SHORT).show();
//            }
//
//        }


//        po.saveInBackground();
        finish();
    }

    public void downloadNotes (){

        //simple notes
        simplesList = Singleton.getInstance().getSimpleNotes();
        ParseQuery<ParseObject> pq = ParseQuery.getQuery("SimpleNotes");
        pq.whereEqualTo("user", ParseUser.getCurrentUser());
        pq.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    Singleton.getInstance().refresh(simplesList);
                    for (int i = 0; i < list.size(); i++) {
                        SimpleNote simpleNote = new SimpleNote(
                                list.get(i).getObjectId(),
                                list.get(i).getUpdatedAt(),
                                list.get(i).getCreatedAt(),
                                list.get(i).getString("name"),
                                list.get(i).getString("text"),
                                list.get(i).getString("subject")
                        ) ;
                        simplesList.add(simpleNote);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        });

        taskList=Singleton.getInstance().getTaskNotes();
        ParseQuery<ParseObject> pq2 = ParseQuery.getQuery("TaskNotes");
        pq2.whereEqualTo("user", ParseUser.getCurrentUser());
        pq2.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    Singleton.getInstance().refresh(taskList);
                    for (int i = 0; i < list.size(); i++) {
                        TaskNote taskNote = new TaskNote(
                                list.get(i).getObjectId(),
                                list.get(i).getUpdatedAt(),
                                list.get(i).getCreatedAt(),
                                list.get(i).getString("name"),
                                list.get(i).getString("text"),
                                list.get(i).getList("date"),
                                list.get(i).getList("time")
                        );
                        taskList.add(taskNote);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        });

        toDoList=Singleton.getInstance().getToDoNotes();
        ParseQuery<ParseObject> pq3 = ParseQuery.getQuery("ToDoNotes");
        pq3.whereEqualTo("user", ParseUser.getCurrentUser());
        pq3.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    Singleton.getInstance().refresh(toDoList);

                    for (int i = 0; i < list.size(); i++) {
                        ToDoNote toDoNote = new ToDoNote(
                                list.get(i).getObjectId(),
                                list.get(i).getUpdatedAt(),
                                list.get(i).getCreatedAt(),
                                list.get(i).getString("name"),
                                list.get(i).getString("text"),
                                list.get(i).getInt("period"),
                                ((ArrayList) list.get(i).getList("doing"))
                        );
                        toDoList.add(toDoNote);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        });


    }
// have to rewrite it
    @Override
    public ArrayList<CheckBox> getListOfDoing(ArrayList listOfTodo) {

        return this.todoCheckbox = listOfTodo;
    }
}




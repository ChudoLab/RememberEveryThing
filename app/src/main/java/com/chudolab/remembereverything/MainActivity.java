package com.chudolab.remembereverything;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chudolab.remembereverything.lists_of_notes.SimpleListActivity;
import com.chudolab.remembereverything.lists_of_notes.TasksListActivity;
import com.chudolab.remembereverything.lists_of_notes.ToDoListActivity;
import com.chudolab.remembereverything.options.Options;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ArrayList<String> gotOptions;
    private Toolbar myToolBar;
    private ActionBarDrawerToggle toggle;
    private ParseObject po;
    private EditText currentNoteText;
    final static int RESULT_SIMPLE_NOTE = 1;
    final static int RESULT_SIMPLE_TODO = 2;
    final static int RESULT_TAB_TASK = 3;
    private TextView calendarShow;
    private TextView reminderShow;
    private TextView topicName;
    private TextView noteName;
    private int noteType;
    private int[] times;
    private int[] dates;
    private String currentNoteId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar
        myToolBar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolBar);

        //Toggle Drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                myToolBar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(toggle);

        //Drawer job with keyboard

        drawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //how to hide a keyboard
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });

        //currentNoteText = (EditText)findViewById(R.id.currentNoteText);
        currentNoteText = new EditText(getApplicationContext());
        currentNoteText.setOnKeyListener(new EditText.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    Toast.makeText(MainActivity.this, "clicked enter", Toast.LENGTH_SHORT).show();
                    Log.e("wow", "clicked");
                    return true;
                }
                Log.e("wow", "clicked out");
                return false;
            }
        });

    }

    public void setNotification(int[] times, String currentNoteText, String currentNoteId) {

        Intent intent = new Intent(this, AlarmReceiver.class);

        intent.putExtra("noteID", currentNoteId);
        intent.putExtra("currentNoteText", currentNoteText);

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, times[0]);
        calendar.set(Calendar.MINUTE, times[1]);
        calendar.set(Calendar.SECOND, 0);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 3444, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), pendingIntent);

    }
    public void sendToCalendar (View v){
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.addCategory(Intent.CATEGORY_APP_CALENDAR);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("title", "Some title");
        intent.putExtra("description", "Some description");
        intent.putExtra("beginTime", System.currentTimeMillis());
        intent.putExtra("endTime", System.currentTimeMillis());
        startActivity(intent);
    }

    public void onSaveButtonClick(View v) {
        if (v.getId() == R.id.buttonSaveClose) {
            currentNoteText = (EditText) findViewById(R.id.currentNoteText);

            String resCurrentNote = currentNoteText.getText().toString();

            if (!resCurrentNote.isEmpty()) {
                po = new ParseObject("SimpleNotes");

               if (noteType == RESULT_SIMPLE_NOTE) {

                    if (!gotOptions.get(0).isEmpty()) {
                        po.put("name", gotOptions.get(0));
                    }
                    if (!gotOptions.get(1).isEmpty()) {
                        po.put("subject", gotOptions.get(1));
                    }

                } else if (noteType == RESULT_SIMPLE_TODO) {
                    //TODO make todo list
                }


                if (noteType == RESULT_TAB_TASK) {
                    if (times != null) {

                        po.put("text", resCurrentNote);

                        po.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(getApplicationContext(), "Saved ", Toast.LENGTH_SHORT).show();
                                    currentNoteId = po.getObjectId();
                                    Log.e("current Id", currentNoteId);

                                } else {
                                    Toast.makeText(getApplicationContext(), "Error ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        setNotification(times, currentNoteText.getText().toString(), currentNoteId);
                        finish();
                    }

                }
                //TODO default name and subject

                po.put("text", resCurrentNote);

                po.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), "Saved ", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(MainActivity.this, "Note is empty!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    // deleting tasks
    public void deleteTask(View v) {
        if (v.getId() == R.id.deleteCalendar) {
            calendarShow.setVisibility(View.GONE);
            v.setVisibility(View.GONE);
            dates = null;
        } else {
            reminderShow.setVisibility(View.GONE); // gone textView
            v.setVisibility(View.GONE);//gone button
            times = null; // null the object

        }

    }

    //getting all options
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == RESULT_CANCELED) {
            noteType = 0;

        } else if (resultCode == RESULT_SIMPLE_NOTE) {
            noteType = RESULT_SIMPLE_NOTE;
            gotOptions = new ArrayList<>();
            gotOptions = intent.getStringArrayListExtra("simpleOptions");

            topicName = (TextView) findViewById(R.id.topicName);
            noteName = (TextView) findViewById(R.id.noteName);
            topicName.setText("Topic: " + gotOptions.get(1));// because i'm super lazy
            noteName.setText("Name: " + gotOptions.get(0));

            topicName.setVisibility(View.VISIBLE);
            noteName.setVisibility(View.VISIBLE);

        } else if (resultCode == RESULT_SIMPLE_TODO) {
            noteType = RESULT_SIMPLE_TODO;

            Toast.makeText(MainActivity.this, "TODO", Toast.LENGTH_SHORT).show();

        } else if (resultCode == RESULT_TAB_TASK) {

            noteType = RESULT_TAB_TASK;

            if (intent.getIntArrayExtra("time") != null) {

                times = intent.getIntArrayExtra("time");
                reminderShow = (TextView) findViewById(R.id.reminderShow);
                reminderShow.setText("You've set reminder on " + times[0] + " hour "
                        + " and " + times[1] + " minutes today");
                reminderShow.setVisibility(View.VISIBLE);
                Button deleteReminder = (Button) findViewById(R.id.deleteReminder);
                deleteReminder.setVisibility(View.VISIBLE);
            }
            if (intent.getIntArrayExtra("date") != null) {

                dates = intent.getIntArrayExtra("date");
                calendarShow = (TextView) findViewById(R.id.calendarShow);
                calendarShow.setText("You've set calendar on " + dates[0] +
                        " day  " + dates[1] + " month " + "and " + dates[2] + " year");
                calendarShow.setVisibility(View.VISIBLE);
                Button deleteCalendar = (Button) findViewById(R.id.deleteCalendar);
                deleteCalendar.setVisibility(View.VISIBLE);
            }
        }
    }

    //drawer menu click
    public void onDrawerMenuClick(MenuItem menuItem) {
        Intent intent;
        if (menuItem.getItemId() == R.id.buttonSimpleNotes) {
            intent = new Intent(getApplicationContext(), ListOfSubjectActivity.class);
        } else if (menuItem.getItemId() == R.id.buttonTodos) {
            intent = new Intent(getApplicationContext(), ToDoListActivity.class);
        } else if (menuItem.getItemId() == R.id.buttonTasks) {
            intent = new Intent(getApplicationContext(), TasksListActivity.class);
        } else {
            Toast.makeText(MainActivity.this, "settings", Toast.LENGTH_SHORT).show();
            intent = new Intent();
        }

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    // this is for all menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        } else if (item.getItemId() == R.id.currentOptions) {
            Intent intent = new Intent(getApplicationContext(), Options.class);
            startActivityForResult(intent, 1);
        }
        return super.onOptionsItemSelected(item);
    }

    // this 2 methods are for Drawer toggle
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }
}

package com.chudolab.remembereverything.main_page;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.chudolab.remembereverything.ListOfSubjectActivity;
import com.chudolab.remembereverything.R;
import com.chudolab.remembereverything.lists_of_notes.TasksListActivity;
import com.chudolab.remembereverything.lists_of_notes.ToDoListActivity;

import view.SlidingTabLayout;

public class MainPageActivity extends AppCompatActivity {
    private ViewPager pager;
    private ViewPagerAdapter adapter;
    private SlidingTabLayout tabs;
    private CharSequence Titles[] = {"Note", "Options"};
    private int Numboftabs = 2;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setViewPager(pager);

        //Toolbar
        Toolbar myToolBar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolBar);

        //DRAWER
        //Toggle Drawer
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
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
            Toast.makeText(MainPageActivity.this, "settings", Toast.LENGTH_SHORT).show();
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
        }
//          else if (item.getItemId() == R.id.currentOptions) {
//            Intent intent = new Intent(getApplicationContext(), Options.class);
//            startActivityForResult(intent, 1);
//        }
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
}



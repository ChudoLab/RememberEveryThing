package com.chudolab.remembereverything.main_page;

import android.graphics.Color;
import android.os.Bundle;
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

import com.chudolab.remembereverything.DrawerAppCompatActivity;
import com.chudolab.remembereverything.R;

import view.SlidingTabLayout;

public class MainPageActivity extends DrawerAppCompatActivity {
    private CharSequence Titles[] = {"Note", "Options"};
    private int Numboftabs = 2;

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
        finish();

//        if (v.getId() == R.id.buttonSaveClose) {
//            currentNoteText = (EditText) findViewById(R.id.currentNoteText);
//
//            String resCurrentNote = currentNoteText.getText().toString();
//
//            if (!resCurrentNote.isEmpty()) {
//                po = new ParseObject("SimpleNotes");
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
    }
//
}



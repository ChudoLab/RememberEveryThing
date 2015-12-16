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

public class MainPageActivity extends DrawerAppCompatActivity {
    private CharSequence Titles[] = {"Note", "Options"};
    private int Numboftabs = 2;
    //Dowloaded from parse
    ArrayList<Note> simplesList;
    ArrayList<Note> toDoList;
    ArrayList<Note> taskList;
    ArrayList<CheckBox> todoCheckbox;
    ArrayList subjectList;

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

    public void downloadNotes() {
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
                                list.get(i).getString("subject"),
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
                                list.get(i).getString("subject"),
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

        subjectList = Singleton.getInstance().getSubjects();
        ParseQuery<ParseObject> pq4 = ParseQuery.getQuery("Subjects");
        pq4.whereEqualTo("user", ParseUser.getCurrentUser());
        pq4.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                   subjectList.removeAll(subjectList);
                    for (int i = 0; i < list.size(); i++) {
                        subjectList.add(list.get(i).getString("subject"));
                    }

                } else {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}




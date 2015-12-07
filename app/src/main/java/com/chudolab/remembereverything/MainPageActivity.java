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
import com.chudolab.remembereverything.type_of_notes.Note;
import com.chudolab.remembereverything.type_of_notes.SimpleNote;
import com.chudolab.remembereverything.type_of_notes.TaskNote;
import com.chudolab.remembereverything.type_of_notes.ToDoNote;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainPageActivity extends AppCompatActivity {

    private ArrayList<Note> simplesList;
    private ArrayList<Note> toDoList;
    private ArrayList<Note> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //downloading user's notes from Parse

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



}

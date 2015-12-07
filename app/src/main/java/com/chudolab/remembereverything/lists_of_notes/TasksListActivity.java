package com.chudolab.remembereverything.lists_of_notes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.chudolab.remembereverything.R;
import com.chudolab.remembereverything.Singleton;
import com.chudolab.remembereverything.one_note_show.TaskNoteActivity;
import com.chudolab.remembereverything.type_of_notes.Note;
import com.chudolab.remembereverything.type_of_notes.TaskNote;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class TasksListActivity extends AppCompatActivity {

    ArrayList<Note> taskList;
    RecyclerView rvTasks;
    ProgressDialog dialog;
    NoteMovieAdapter adapter;

    @Override
    protected void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_list);
        rvTasks = (RecyclerView) findViewById(R.id.lvTasks);

        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        rvTasks.setLayoutManager(linearLayoutManager);
        taskList = Singleton.getInstance().getTaskNotes();
        adapter = new NoteMovieAdapter(this, "Task", taskList, R.layout.task_note_for_list, R.id.tvNameTask, R.id.tvDateTask, R.id.tvTextTask);
        rvTasks.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new NoteTouchHelper(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rvTasks);

    }
}
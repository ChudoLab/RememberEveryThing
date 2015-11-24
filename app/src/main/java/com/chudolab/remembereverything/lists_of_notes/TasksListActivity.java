package com.chudolab.remembereverything.lists_of_notes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class TasksListActivity extends AppCompatActivity {
    ArrayList<Note> taskList;
    GridView lvTasks;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_list);
        lvTasks = (GridView) findViewById(R.id.lvTasks);
        taskList = Singleton.getInstance().getTaskNotes();
        NoteAdapter adapter = new NoteAdapter(getApplicationContext(), taskList, R.layout.task_note_for_list, R.id.tvNameTask, R.id.tvDisTask, R.id.tvTextTask);
        lvTasks.setAdapter(adapter);
        ParseQuery<ParseObject> pq = ParseQuery.getQuery("TaskNotes");

        dialog = new ProgressDialog(this);
        dialog.setTitle("Downloading notes");
        dialog.show();

        pq.findInBackground(new FindCallback<ParseObject>() {
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
                                list.get(i).getDate("date")
                        );
                        taskList.add(taskNote);
                    }
                    lvTasks.invalidateViews();
                    dialog.cancel();
                } else {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        });
        lvTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), TaskNoteActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }
}

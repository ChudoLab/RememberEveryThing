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
import com.chudolab.remembereverything.one_note_show.ToDoNoteActivity;
import com.chudolab.remembereverything.type_of_notes.Note;
import com.chudolab.remembereverything.type_of_notes.ToDoNote;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ToDoListActivity extends AppCompatActivity {
    ProgressDialog dialog;
    GridView lvToDo;
    ArrayList<Note> toDoList;

    //ArrayList<> listDoing;
//ListView lvDoing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        lvToDo = (GridView) findViewById(R.id.lvToDo);
        toDoList = Singleton.getInstance().getToDoNotes();
        NoteAdapter adapter = new NoteAdapter(getApplicationContext(), toDoList, R.layout.todo_note_for_list, R.id.tvToDoName, R.id.lvToDo);
        lvToDo.setAdapter(adapter);

//lvDoing = (ListView) findViewById(R.id.lvDoing);
        ParseQuery<ParseObject> pq = ParseQuery.getQuery("ToDoNotes");

        dialog = new ProgressDialog(this);
        dialog.setTitle("Downloading notes");
        dialog.show();

        pq.findInBackground(new FindCallback<ParseObject>() {
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
// listDoing.addAll(toDoNote.getDoing());

// lvDoing.setAdapter(new ToDoAdapter(getApplicationContext(),listDoing));
// lvDoing.invalidateViews();
                    }
                    lvToDo.invalidateViews();
                    dialog.cancel();
                } else {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        });
        lvToDo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ToDoNoteActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });


    }
}

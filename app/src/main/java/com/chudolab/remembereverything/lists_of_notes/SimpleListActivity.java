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
import com.chudolab.remembereverything.one_note_show.SimpleNoteActivity;
import com.chudolab.remembereverything.type_of_notes.Note;
import com.chudolab.remembereverything.type_of_notes.SimpleNote;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class SimpleListActivity extends AppCompatActivity {
    ArrayList<Note> simplesList;
    GridView lvSimpls;
    ProgressDialog dialog;

    @Override
    protected void onStart() {
        super.onStart();
        lvSimpls.invalidateViews();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);
        lvSimpls = (GridView) findViewById(R.id.lvSimple);
        simplesList = Singleton.getInstance().getSimpleNotes();
        NoteAdapter adapter = new NoteAdapter(this,simplesList,R.layout.simple_note_for_list,R.id.tvSimpleName,R.id.tvSimpleDesc,R.id.tvSimpleText);
        lvSimpls.setAdapter(adapter);
        String subject = getIntent().getStringExtra("subject");
        ParseUser.getCurrentUser();
        ParseQuery<ParseObject> pq = ParseQuery.getQuery("SimpleNotes");

        dialog =new ProgressDialog(this);
        dialog.setTitle("Downloading notes");
        dialog.show();
        pq.whereEqualTo("subject", subject);
// pq.whereEqualTo("users",ParseUser.getCurrentUser());
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
                    lvSimpls.invalidateViews();
                    dialog.cancel();
                } else {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        });
        lvSimpls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),SimpleNoteActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }

}

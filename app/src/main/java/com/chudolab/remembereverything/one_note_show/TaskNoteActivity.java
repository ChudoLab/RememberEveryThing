package com.chudolab.remembereverything.one_note_show;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chudolab.remembereverything.R;
import com.chudolab.remembereverything.Singleton;
import com.chudolab.remembereverything.type_of_notes.Note;
import com.chudolab.remembereverything.type_of_notes.SimpleNote;
import com.chudolab.remembereverything.type_of_notes.TaskNote;
import com.parse.ParseObject;

public class TaskNoteActivity extends AppCompatActivity {
    EditText name;
    EditText date;
    EditText time;
    EditText text;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_note);

        position = getIntent().getIntExtra("position", 0);

        //Toolbar
        Toolbar myToolBar = (Toolbar) findViewById(R.id.my_tool_bar_one_show);
        setSupportActionBar(myToolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("");

        name = (EditText) findViewById(R.id.etNameTask);
        date = (EditText) findViewById(R.id.etDateTask);
        time = (EditText) findViewById(R.id.etTimeTask);
        text = (EditText) findViewById(R.id.etTextTask);
        TaskNote note = ((TaskNote) Singleton.getInstance().getTaskNotes().get(position));


        name.setText(note.getName());
        date.setText("date: " + note.getDate().get(0) + "." + note.getDate().get(1) + "." + note.getDate().get(2));
        time.setText("time: " + note.getTime().get(0) + ":" + note.getTime().get(1));
        text.setText(Singleton.getInstance().getTaskNotes().get(position).getText());
        name.setTextColor(Color.BLACK);
        text.setTextColor(Color.BLACK);

        date.setTextColor(Color.BLACK);

//        Button btnSave = (Button) findViewById(R.id.btnSaveTaskNote);
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Note note = Singleton.getInstance().getTaskNotes().get(position);
//                ParseObject object = ParseObject.createWithoutData("TaskNotes", note.getObjectId());
//                object.put("name", name.getText().toString());
//                object.put("text", text.getText().toString());
//                //time & date save in parse!!!
//                object.saveInBackground();
//                note.setName(name.getText().toString());
//                note.setText(text.getText().toString());

        //edit date!!

        //edit time!!!
//
//                finish();
//            }
//        });

    }

    //toolbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu_one_show, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.okButton) {
            Note note = Singleton.getInstance().getTaskNotes().get(position);
            ParseObject object = ParseObject.createWithoutData("TaskNotes", note.getObjectId());
            object.put("name", name.getText().toString());
            object.put("text", text.getText().toString());
            //time & date save in parse!!!
            object.saveInBackground();
            note.setName(name.getText().toString());
            note.setText(text.getText().toString());
            finish();
        } else {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
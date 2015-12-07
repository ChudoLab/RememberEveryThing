package com.chudolab.remembereverything.one_note_show;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_note);

        final int position = getIntent().getIntExtra("position", 0);

        final EditText name = (EditText) findViewById(R.id.etNameTask);
        EditText date = (EditText) findViewById(R.id.etDateTask);
        EditText time = (EditText) findViewById(R.id.etTimeTask);
        final EditText text = (EditText) findViewById(R.id.etTextTask);
        TaskNote note =((TaskNote) Singleton.getInstance().getTaskNotes().get(position));


        name.setText(note.getName());
        date.setText("date: " + note.getDate().get(0) + "." + note.getDate().get(1)+"."+note.getDate().get(2));
        time.setText("time: " + note.getTime().get(0) + ":" + note.getTime().get(1));
        text.setText(Singleton.getInstance().getTaskNotes().get(position).getText());
        name.setTextColor(Color.BLACK);
        text.setTextColor(Color.BLACK);

        date.setTextColor(Color.BLACK);
        Button btnSave = (Button) findViewById(R.id.btnSaveTaskNote);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = Singleton.getInstance().getTaskNotes().get(position);
                ParseObject object = ParseObject.createWithoutData("TaskNotes", note.getObjectId());
                object.put("name", name.getText().toString());
                object.put("text", text.getText().toString());
                //time & date save in parse!!!
                object.saveInBackground();
                note.setName(name.getText().toString());
                note.setText(text.getText().toString());

               //edit date!!

                //edit time!!!

                finish();
            }
        });
    }
}
package com.chudolab.remembereverything.one_note_show;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chudolab.remembereverything.R;
import com.chudolab.remembereverything.Singleton;
import com.chudolab.remembereverything.type_of_notes.Note;
import com.chudolab.remembereverything.type_of_notes.SimpleNote;
import com.parse.ParseObject;

public class SimpleNoteActivity extends AppCompatActivity {
    int position;
    Note note ;
    EditText name;
    EditText subject;
    EditText text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_note);

        position = getIntent().getIntExtra("position",0);
        note = Singleton.getInstance().getSimpleNotes().get(position);
        name = (EditText) findViewById(R.id.etNameOfSimpleNote);
        subject = (EditText) findViewById(R.id.etSubjectOfSimpleNote);
        text = (EditText) findViewById(R.id.etTextOfSimpleNote);

        name.setText(note.getName());
        subject.setText(note.getDescription());
        text.setText(note.getText());
        name.setTextColor(Color.BLACK);
        text.setTextColor(Color.BLACK);
        subject.setTextColor(Color.BLACK);

        Button btnSave = (Button) findViewById(R.id.btnSaveSimpleNote);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject object = ParseObject.createWithoutData("SimpleNotes", note.getObjectId());
                object.put("name", name.getText().toString());
                object.put("subject", subject.getText().toString());
                object.put("text", text.getText().toString());
                object.saveInBackground();
                note.setName(name.getText().toString());
                note.setText(text.getText().toString());
                ((SimpleNote) note).setSubject(subject.getText().toString());
               // notifyAll();
                finish();
            }
        });

    }
    //add to second button !!!
    public void close(View v){

    }


}

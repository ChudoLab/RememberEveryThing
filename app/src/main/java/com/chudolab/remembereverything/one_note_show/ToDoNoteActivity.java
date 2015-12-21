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
import com.parse.ParseObject;

public class ToDoNoteActivity extends AppCompatActivity {
    EditText name;
    EditText text;
    EditText period;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_note);

        final int position = getIntent().getIntExtra("position", 0);

        name = (EditText) findViewById(R.id.etNameOfSimpleNote);
        period = (EditText) findViewById(R.id.etSubjectOfSimpleNote);
        text = (EditText) findViewById(R.id.etTextOfSimpleNote);

        name.setText(Singleton.getInstance().getToDoNotes().get(position).getName());
        period.setText(Singleton.getInstance().getToDoNotes().get(position).getSubject());
        text.setText(Singleton.getInstance().getToDoNotes().get(position).getText());
        name.setTextColor(Color.BLACK);
        text.setTextColor(Color.BLACK);
        period.setTextColor(Color.BLACK);

//        Button btnSave = (Button) findViewById(R.id.btnSaveSimpleNote);
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Note note = Singleton.getInstance().getToDoNotes().get(position);
//                ParseObject object = ParseObject.createWithoutData("ToDoNotes", note.getObjectId());
//                object.put("name", name.getText().toString());
//                //object.put("subject", subject.getText().toString());
//                //object.put("text", text.getText().toString());
//                object.saveInBackground();
//                note.setName(name.getText().toString());
//                note.setText(text.getText().toString());
//                // ((TaskNote) note).setSubject(subject.getText().toString());
//                finish();
//            }
//        });
    }
}

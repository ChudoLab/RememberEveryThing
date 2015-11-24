package com.chudolab.remembereverything.one_note_show;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.chudolab.remembereverything.R;
import com.chudolab.remembereverything.Singleton;

public class TaskNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_note);

        int position = getIntent().getIntExtra("position", 0);

        EditText name = (EditText) findViewById(R.id.etNameOfSimpleNote);
        EditText date = (EditText) findViewById(R.id.etSubjectOfSimpleNote);
        EditText text = (EditText) findViewById(R.id.etTextOfSimpleNote);

        name.setText(Singleton.getInstance().getTaskNotes().get(position).getName());
        date.setText(Singleton.getInstance().getTaskNotes().get(position).getDescription());
        text.setText(Singleton.getInstance().getTaskNotes().get(position).getText());
        name.setTextColor(Color.BLACK);
        text.setTextColor(Color.BLACK);
        date.setTextColor(Color.BLACK);
    }
}
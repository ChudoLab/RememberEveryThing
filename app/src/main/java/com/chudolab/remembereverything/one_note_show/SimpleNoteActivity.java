package com.chudolab.remembereverything.one_note_show;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.chudolab.remembereverything.R;
import com.chudolab.remembereverything.Singleton;

public class SimpleNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_note);

        int position = getIntent().getIntExtra("position",0);

        EditText name = (EditText) findViewById(R.id.etNameOfSimpleNote);
        EditText subject = (EditText) findViewById(R.id.etSubjectOfSimpleNote);
        EditText text = (EditText) findViewById(R.id.etTextOfSimpleNote);

        name.setText(Singleton.getInstance().getSimpleNotes().get(position).getName());
        subject.setText(Singleton.getInstance().getSimpleNotes().get(position).getDescription());
        text.setText(Singleton.getInstance().getSimpleNotes().get(position).getText());
        name.setTextColor(Color.BLACK);
        text.setTextColor(Color.BLACK);
        subject.setTextColor(Color.BLACK);

    }



}

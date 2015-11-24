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

public class ToDoNoteActivity extends AppCompatActivity {
    EditText tvName;
    EditText tvText;
    EditText tvPeriod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_note);

        int position = getIntent().getIntExtra("position", 0);

        tvName = (EditText) findViewById(R.id.etNameOfSimpleNote);
        tvPeriod = (EditText) findViewById(R.id.etSubjectOfSimpleNote);
        tvText = (EditText) findViewById(R.id.etTextOfSimpleNote);

        tvName.setText(Singleton.getInstance().getToDoNotes().get(position).getName());
        tvPeriod.setText(Singleton.getInstance().getToDoNotes().get(position).getDescription());
        tvText.setText(Singleton.getInstance().getToDoNotes().get(position).getText());
        tvName.setTextColor(Color.BLACK);
        tvText.setTextColor(Color.BLACK);
        tvText.setTextColor(Color.BLACK);

//it does'n metter:)
        Button btnAdd = (Button) findViewById(R.id.btnSaveSimpleNote);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }
}

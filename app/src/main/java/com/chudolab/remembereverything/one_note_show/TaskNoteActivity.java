package com.chudolab.remembereverything.one_note_show;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.chudolab.remembereverything.R;
import com.chudolab.remembereverything.Singleton;

public class TaskNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_note);

        //Toolbar
        Toolbar myToolBar = (Toolbar)findViewById(R.id.my_tool_bar_one_show);
        setSupportActionBar(myToolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("");

        int position = getIntent().getIntExtra("position", 0);

        EditText name = (EditText) findViewById(R.id.etNameOfSimpleNote);
        EditText date = (EditText) findViewById(R.id.etSubjectOfSimpleNote);
        EditText text = (EditText) findViewById(R.id.etTextOfSimpleNote);

        name.setText(Singleton.getInstance().getTaskNotes().get(position).getName());
//        date.setText(Singleton.getInstance().getTaskNotes().get(position).getDescription());
        text.setText(Singleton.getInstance().getTaskNotes().get(position).getText());
        name.setTextColor(Color.BLACK);
        text.setTextColor(Color.BLACK);
        date.setTextColor(Color.BLACK);
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

        if(item.getItemId() == R.id.okButton){
            finish();
        }
        else  {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
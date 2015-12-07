package com.chudolab.remembereverything.lists_of_notes;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.GridView;
import android.widget.Toast;

import com.chudolab.remembereverything.R;
import com.chudolab.remembereverything.Singleton;
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
    RecyclerView rvSimpls;
    ProgressDialog dialog;
    NoteMovieAdapter adapter;

    @Override
    protected void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);
        String subject = getIntent().getStringExtra("subject");
        rvSimpls = (RecyclerView) findViewById(R.id.lvSimple);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        rvSimpls.setLayoutManager(linearLayoutManager);

        simplesList = Singleton.getInstance().getSimpleNotes();

         adapter = new NoteMovieAdapter(this,"Simple",simplesList,R.layout.simple_note_for_list,R.id.tvSimpleName,R.id.tvSimpleDesc,R.id.tvSimpleText);
        rvSimpls.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new NoteTouchHelper(adapter);

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rvSimpls);

    }

}

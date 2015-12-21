package com.chudolab.remembereverything.lists_of_notes;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ActionMenuView;
import android.widget.Toast;

import com.chudolab.remembereverything.DrawerAppCompatActivity;
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

public class SimpleListActivity extends DrawerAppCompatActivity {
    ArrayList<Note> simplesList;
    RecyclerView rvSimpls;
    ProgressDialog dialog;
    NoteMovieAdapter adapterAll;
    NoteMovieAdapter adapter;
String subject;
    @Override
    protected void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rvSimpls = (RecyclerView) findViewById(R.id.lvSimple);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        rvSimpls.setLayoutManager(linearLayoutManager);

        simplesList = Singleton.getInstance().getSimpleNotes();

        adapter = new NoteMovieAdapter(this, "Simple", simplesList, R.layout.simple_note_for_list, R.id.tvSimpleName, R.id.tvSimpleDesc, R.id.tvSimpleText);
        rvSimpls.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new NoteTouchHelper(adapter);

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rvSimpls);

        //String subject = getIntent().getStringExtra("subject");
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(getToolbarMenu(), menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add("All");
        int i = 0;
        for(String subject: Singleton.getInstance().getSubjects()){
            menu.add(subject);
            menu.add(0,i, i,subject);
            i++;
        }


        return true;
    }

    // this is for all menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals("All")){
            adapter = new NoteMovieAdapter(this, "Simple", simplesList, R.layout.simple_note_for_list, R.id.tvSimpleName, R.id.tvSimpleDesc, R.id.tvSimpleText);
            rvSimpls.setAdapter(adapter);
            ItemTouchHelper.Callback callback = new NoteTouchHelper(adapter);

            ItemTouchHelper helper = new ItemTouchHelper(callback);
            helper.attachToRecyclerView(rvSimpls);
            adapter.notifyDataSetChanged();
        }else{
            getNotesWhithSubject(item.getItemId());}
        return super.onOptionsItemSelected(item);

    }
    public void getNotesWhithSubject(int position){
        ArrayList<Note> notes = new ArrayList<>();
        subject=Singleton.getInstance().getSubjects().get(position);

        for(Note note: Singleton.getInstance().getSimpleNotes()){
            if(((SimpleNote)note).getSubject().equalsIgnoreCase(subject)){
                notes.add(note);
            }
        }
        adapter = new NoteMovieAdapter(this, "Simple", notes, R.layout.simple_note_for_list, R.id.tvSimpleName, R.id.tvSimpleDesc, R.id.tvSimpleText);
        rvSimpls.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new NoteTouchHelper(adapter);

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rvSimpls);
        adapter.notifyDataSetChanged();

    }

    public static void remove(int i) {
        Singleton.getInstance().getSimpleNotes().remove(i);
    }

    protected void onStop() {
        super.onStop();

        DrawerLayout dr = (DrawerLayout) findViewById(R.id.drawerLayout);
        dr.closeDrawers();

    }

    @Override
    public Toolbar getToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_lists);
        myToolbar.setTitle("Notes");

//        myToolbar.setLogo(R.drawable.ic_action_back);

        return myToolbar;
    }

    public int getToolbarMenu() {
        return R.menu.toolbar_menu_list;
    }

    @Override
    public View getContentView() {
        View contentView = getLayoutInflater().inflate(R.layout.activity_simple_list, null);
        return contentView;
    }

}

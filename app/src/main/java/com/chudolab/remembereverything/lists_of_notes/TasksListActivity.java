package com.chudolab.remembereverything.lists_of_notes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.chudolab.remembereverything.DrawerAppCompatActivity;
import com.chudolab.remembereverything.R;
import com.chudolab.remembereverything.Singleton;
import com.chudolab.remembereverything.one_note_show.TaskNoteActivity;
import com.chudolab.remembereverything.type_of_notes.Note;
import com.chudolab.remembereverything.type_of_notes.SimpleNote;
import com.chudolab.remembereverything.type_of_notes.TaskNote;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class TasksListActivity extends DrawerAppCompatActivity {

    ArrayList<Note> taskList;
    RecyclerView rvTasks;
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
        rvTasks = (RecyclerView) findViewById(R.id.lvTasks);

        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        rvTasks.setLayoutManager(linearLayoutManager);
        taskList = Singleton.getInstance().getTaskNotes();
        adapter = new NoteMovieAdapter(this, "Task", taskList, R.layout.task_note_for_list, R.id.tvNameTask, R.id.tvSubjectTask, R.id.tvTextTask);
        rvTasks.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new NoteTouchHelper(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rvTasks);

    }
    protected void onStop() {
        super.onStop();

        DrawerLayout dr = (DrawerLayout) findViewById(R.id.drawerLayout);
        dr.closeDrawers();

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(getToolbarMenu(), menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add("All");
        int i = 0;
        for(String subject: Singleton.getInstance().getSubjects()){
            menu.add(0,i,i,subject);
            i++;
        }

        return true;
    }

    // this is for all menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals("All")){

            adapter = new NoteMovieAdapter(this, "Task", taskList, R.layout.task_note_for_list, R.id.tvNameTask, R.id.tvSubjectTask, R.id.tvTextTask);
            rvTasks.setAdapter(adapter);
            ItemTouchHelper.Callback callback = new NoteTouchHelper(adapter);
            ItemTouchHelper helper = new ItemTouchHelper(callback);
            helper.attachToRecyclerView(rvTasks);
            adapter.notifyDataSetChanged();
        }else{
            getNotesWhithSubject(item.getItemId());}
        return super.onOptionsItemSelected(item);

    }
    public void getNotesWhithSubject(int position){
        ArrayList<Note> notes = new ArrayList<>();
        subject=Singleton.getInstance().getSubjects().get(position);

        for(Note note: Singleton.getInstance().getTaskNotes()){
            if((note).getSubject().equalsIgnoreCase(subject)){
                notes.add(note);
            }
        }

        adapter = new NoteMovieAdapter(this, "Task", notes, R.layout.task_note_for_list, R.id.tvNameTask, R.id.tvSubjectTask, R.id.tvTextTask);
        rvTasks.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new NoteTouchHelper(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rvTasks);
    }



    @Override
    public Toolbar getToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_lists);
        myToolbar.setTitle("Tasks");
        return myToolbar;
    }
    public int getToolbarMenu(){
        return R.menu.toolbar_menu_list;
    }

    @Override
    public View getContentView() {
        View contentView = getLayoutInflater().inflate(R.layout.activity_tasks_list,null);
        return contentView;
    }
}
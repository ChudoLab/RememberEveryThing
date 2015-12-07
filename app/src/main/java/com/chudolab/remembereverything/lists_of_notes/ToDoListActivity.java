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
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.chudolab.remembereverything.DrawerAppCompatActivity;
import com.chudolab.remembereverything.R;
import com.chudolab.remembereverything.Singleton;
import com.chudolab.remembereverything.one_note_show.ToDoNoteActivity;
import com.chudolab.remembereverything.type_of_notes.Note;
import com.chudolab.remembereverything.type_of_notes.ToDoNote;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ToDoListActivity extends DrawerAppCompatActivity {

    ProgressDialog dialog;
    RecyclerView rvToDo;
    ArrayList<Note> toDoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rvToDo = (RecyclerView) findViewById(R.id.lvToDo);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);
        rvToDo.setLayoutManager(linearLayoutManager);
        toDoList = Singleton.getInstance().getToDoNotes();

        final NoteMovieAdapter adapter = new NoteMovieAdapter(this, "ToDo", toDoList, R.layout.todo_note_for_list, R.id.tvToDoName, R.id.lvDoing);
        rvToDo.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new NoteTouchHelper(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rvToDo);

        ParseQuery<ParseObject> pq = ParseQuery.getQuery("ToDoNotes");

        dialog = new ProgressDialog(this);
        dialog.setTitle("Downloading notes");
        dialog.show();

        pq.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    Singleton.getInstance().refresh(toDoList);
                    adapter.listOfNotes.removeAll(adapter.listOfNotes);
                    for (int i = 0; i < list.size(); i++) {
                        ToDoNote toDoNote = new ToDoNote(
                                list.get(i).getObjectId(),
                                list.get(i).getUpdatedAt(),
                                list.get(i).getCreatedAt(),
                                list.get(i).getString("name"),
                                list.get(i).getString("text"),
                                list.get(i).getInt("period"),
                                ((ArrayList) list.get(i).getList("doing"))
                        );
                        toDoList.add(toDoNote);
                    }
                    adapter.notifyDataSetChanged();
                    dialog.cancel();
                } else {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    protected void onStop() {
        super.onStop();

        DrawerLayout dr = (DrawerLayout) findViewById(R.id.drawerLayout);
        dr.closeDrawers();

    }
    @Override
    public Toolbar getToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_lists);
        myToolbar.setTitle("Lists");

        return myToolbar;
    }

    @Override
    public View getContentView() {
        View contentView = getLayoutInflater().inflate(R.layout.activity_to_do_list,null);
        return contentView;
    }

    public int getToolbarMenu(){
        return R.menu.toolbar_menu_list;
    }

    public static void remove(int i){

        //Singleton.getInstance().getToDoNotes().remove(i);
    }
}
package com.chudolab.remembereverything.lists_of_notes;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
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

    @Override
    protected void onStart() {
        super.onStart();
        rvSimpls.invalidate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("simples ", " " +  Singleton.getInstance().getSimpleNotes().size());
        rvSimpls = (RecyclerView) findViewById(R.id.lvSimple);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        rvSimpls.setLayoutManager(linearLayoutManager);

        simplesList = Singleton.getInstance().getSimpleNotes();
        final NoteMovieAdapter adapter = new NoteMovieAdapter(this,"Simple",simplesList,R.layout.simple_note_for_list,R.id.tvSimpleName,R.id.tvSimpleDesc,R.id.tvSimpleText);
        rvSimpls.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new NoteTouchHelper(adapter);

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rvSimpls);

        String subject = getIntent().getStringExtra("subject");
        ParseUser.getCurrentUser();
        ParseQuery<ParseObject> pq = ParseQuery.getQuery("SimpleNotes");

        dialog = new ProgressDialog(this);
        dialog.setTitle("Downloading notes");
        dialog.show();
        pq.whereEqualTo("subject", subject);

        pq.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    Singleton.getInstance().refresh(simplesList);
                    for (int i = 0; i < list.size(); i++) {
                        SimpleNote simpleNote = new SimpleNote(
                                list.get(i).getObjectId(),
                                list.get(i).getUpdatedAt(),
                                list.get(i).getCreatedAt(),
                                list.get(i).getString("name"),
                                list.get(i).getString("text"),
                                list.get(i).getString("subject")
                        ) ;
                        simplesList.add(simpleNote);

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
        myToolbar.setTitle("Notes");

//        myToolbar.setLogo(R.drawable.ic_action_back);

        return myToolbar;
    }
    public int getToolbarMenu(){
        return R.menu.toolbar_menu_list;
    }

    @Override
    public View getContentView() {
        View contentView = getLayoutInflater().inflate(R.layout.activity_simple_list,null);
        return contentView;
    }

    public static void remove(int i){
        Singleton.getInstance().getSimpleNotes().remove(i);
    }
}

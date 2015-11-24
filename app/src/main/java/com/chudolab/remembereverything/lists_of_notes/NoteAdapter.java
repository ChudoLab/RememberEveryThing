package com.chudolab.remembereverything.lists_of_notes;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chudolab.remembereverything.R;
import com.chudolab.remembereverything.type_of_notes.Note;
import com.chudolab.remembereverything.type_of_notes.ToDoNote;

import java.util.ArrayList;

/**
 * Created by ASUS on 10.11.2015.
 */
public class NoteAdapter extends BaseAdapter {

    private Context context;
    private int idOfNoteView;
    private int idOfNoteName;
    private int idOfNoteInform;
    private int idOfNoteText;
    private ArrayList<Note> listOfNotes;
    private int idOfDoingList;
//private ArrayList<String> listOfSubject;
//private String listOfdoing;

    public NoteAdapter(Context context, ArrayList<Note> listOfNotes, int idOfNoteView, int idOfNoteName, int idOfNoteInform, int idOfNoteText) {
        this.context = context;
        this.idOfNoteView = idOfNoteView;
        this.idOfNoteName = idOfNoteName;
        this.idOfNoteInform = idOfNoteInform;
        this.idOfNoteText = idOfNoteText;
        this.listOfNotes = listOfNotes;
    }

    public NoteAdapter(Context context, ArrayList<Note> listOfNotes, int idOfNoteView, int idOfNoteName, int idOfDoingList) {
        this.context = context;
        this.idOfNoteView = idOfNoteView;
        this.idOfNoteName = idOfNoteName;
        this.listOfNotes = listOfNotes;
        this.idOfDoingList = idOfDoingList;

    }


    @Override
    public int getCount() {
        return listOfNotes.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutOfNote = inflater.inflate(idOfNoteView, parent, false);

        Note note = listOfNotes.get(position);

        if (this.idOfDoingList == 0) {
            TextView tvNoteName = (TextView) layoutOfNote.findViewById(idOfNoteName);
            tvNoteName.setText(tvNoteName.getText() + " " + note.getName());
            tvNoteName.setTextColor(Color.BLACK);

            TextView tvText = (TextView) layoutOfNote.findViewById(idOfNoteText);
            tvText.setText(note.getText());
            tvText.setTextColor(Color.BLACK);

            TextView tvNoteDescription = (TextView) layoutOfNote.findViewById(idOfNoteInform);
            tvNoteDescription.setText(tvNoteDescription.getText() + " " + note.getDescription());
            tvNoteName.setTextColor(Color.BLACK);

        } else {

            TextView tvNoteName = (TextView) layoutOfNote.findViewById(idOfNoteName);
            tvNoteName.setText(tvNoteName.getText() + " " + note.getName());
            tvNoteName.setTextColor(Color.BLACK);

            ListView lvDoing = (ListView) layoutOfNote.findViewById(R.id.lvDoing);

            lvDoing.setAdapter(new ToDoAdapter(context, R.layout.doing_for_list, ((ToDoNote) note).getDoing()));
            lvDoing.invalidate();
        }
        return layoutOfNote;
    }
}

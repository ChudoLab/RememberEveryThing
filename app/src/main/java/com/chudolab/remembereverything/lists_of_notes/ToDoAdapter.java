package com.chudolab.remembereverything.lists_of_notes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;

import com.chudolab.remembereverything.R;
import com.chudolab.remembereverything.Singleton;

import java.util.ArrayList;

/**
 * Created by Chudo on 24.11.2015.
 */
public class  ToDoAdapter extends BaseAdapter {
    private Context context;
    private int idOfNoteView;
    private ArrayList<String> listOfDoing;
//private ArrayList<String> listOfSubject;
//private String listOfdoing;

    public ToDoAdapter(Context context, int idOfNoteView, ArrayList<String> listOfDoing) {
        this.context = context;
        this.idOfNoteView = idOfNoteView;
        this.listOfDoing = listOfDoing;
    }


    @Override
    public int getCount() {
        return listOfDoing.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutOfNote = inflater.inflate(idOfNoteView, parent, false);

        final CheckBox cbDoing = (CheckBox) layoutOfNote.findViewById(R.id.checkBox);
        cbDoing.setText(listOfDoing.get(position));
        cbDoing.setTextColor(Color.BLACK);
        cbDoing.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (cbDoing.isChecked() == true) {

        }
    }
});


        return layoutOfNote;
    }
}


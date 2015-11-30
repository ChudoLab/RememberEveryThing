package com.chudolab.remembereverything.main_page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.chudolab.remembereverything.CurrentTodoAdapter;
import com.chudolab.remembereverything.R;

import java.util.ArrayList;


public class CurrentNoteTab extends Fragment {
    EditText currentNoteText;

    public EditText getCurrentNoteText() {
        return currentNoteText;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.current_note_create_tab, container, false);

        ArrayList listOfDoing = new ArrayList();
        ListView currentTodoList = (ListView)v.findViewById(R.id.currentTodoNoteText);
        CurrentTodoAdapter toDoAdapter = new CurrentTodoAdapter(getActivity().getApplicationContext(), listOfDoing, this);
        currentTodoList.setAdapter(toDoAdapter);

        return v;
    }
}

package com.chudolab.remembereverything.main_page;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.chudolab.remembereverything.CurrentTodoAdapter;
import com.chudolab.remembereverything.R;
import com.chudolab.remembereverything.Singleton;

import java.util.ArrayList;


public class CurrentNoteTab extends Fragment {
    private OnGetListOfTodo mCallback;
    private ArrayList<CheckBox> listOfDoing;

    public ArrayList<CheckBox> getList() {

        return mCallback.getListOfDoing(listOfDoing);
    }

    public interface OnGetListOfTodo {
        ArrayList<CheckBox> getListOfDoing(ArrayList listOfTodo);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnGetListOfTodo) activity;
        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnGetList");
            Log.e("have to ","implement");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.current_note_create_tab, container, false);


        listOfDoing = new ArrayList<CheckBox>();
        ListView currentTodoList = (ListView) v.findViewById(R.id.currentTodoNoteText);
        CurrentTodoAdapter toDoAdapter = new CurrentTodoAdapter(getActivity().getApplicationContext(),
                listOfDoing, this);
        currentTodoList.setAdapter(toDoAdapter);

        ArrayList<String> existingTopics = Singleton.getInstance().getSubjects();
        final ArrayAdapter<String> topicAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.support_simple_spinner_dropdown_item, existingTopics);
        topicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinnerTopics = (Spinner) v.findViewById(R.id.existingTopics);
        spinnerTopics.setAdapter(topicAdapter);

        Button buttonSave = (Button)v.findViewById(R.id.buttonSaveClose);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getList();
            }
        });

//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
        //TODO CLEAR it
        existingTopics.add("Thoughts");
        existingTopics.add("Recipe");
        spinnerTopics.setSelection(1);
        Singleton.getInstance().setSubjects(existingTopics);
        for (int i = 0; i <existingTopics.size() ; i++) {
            System.out.println(existingTopics.get(i));
        }

        return v;
    }
}

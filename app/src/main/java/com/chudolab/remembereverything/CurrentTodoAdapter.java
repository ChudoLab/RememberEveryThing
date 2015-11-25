package com.chudolab.remembereverything;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Chudo on 25.11.2015.
 */
public class CurrentTodoAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> listOfTodo;


    public CurrentTodoAdapter(Context context, ArrayList<String> listOfTodo) {
        this.context = context;
        this.listOfTodo = listOfTodo;

        listOfTodo.add(0, "Type here");
    }


    @Override
    public int getCount() {
        return listOfTodo.size();
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
        View todoLayout = inflater.inflate(R.layout.current_todo, parent, false);

        CheckBox cbDoing = (CheckBox) todoLayout.findViewById(R.id.checkBox);


        EditText todoText = (EditText) todoLayout.findViewById(R.id.todoText);
        todoText.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(todoText, InputMethodManager.SHOW_IMPLICIT);
        todoText.setText(listOfTodo.get(position));
        todoText.setTextColor(Color.BLACK);

        Button addTodo = (Button) todoLayout.findViewById(R.id.addTodo);

        if (position == (listOfTodo.size() - 1)) {
            addTodo.setVisibility(View.VISIBLE);
        } else if (position != (listOfTodo.size() - 1)) {
            addTodo.setVisibility(View.INVISIBLE);
        }

        Button delTodo = (Button) todoLayout.findViewById(R.id.deleteTodo);

        addTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = "";
                listOfTodo.add(temp);

                notifyDataSetChanged();
            }


        });
        delTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listOfTodo.size() == 1){
                    //TODO make edit text appiar if you delete the last
                }
                listOfTodo.remove(position);
                notifyDataSetChanged();
            }
        });


        return todoLayout;
    }
}

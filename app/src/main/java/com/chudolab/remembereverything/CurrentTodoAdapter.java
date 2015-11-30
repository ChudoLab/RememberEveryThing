package com.chudolab.remembereverything;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.chudolab.remembereverything.type_of_notes.CurrentTodoNote;

import java.util.ArrayList;

/**
 * Created by Chudo on 25.11.2015.
 */
public class CurrentTodoAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CheckBox> listOfTodo;
    private CheckBox todoCheckbox;
    private Fragment currNoteTab;

    public CurrentTodoAdapter(Context context, ArrayList<CheckBox> listOfTodo, Fragment currNoteTab) {
        this.context = context;
        this.listOfTodo = listOfTodo;
        this.currNoteTab = currNoteTab;

        if (listOfTodo.size() == 0) {
            this.listOfTodo = new ArrayList<>();
            CheckBox emptyCheckbox = new CheckBox(context);
            emptyCheckbox.setText("");
            emptyCheckbox.setChecked(false);
            this.listOfTodo.add(0, emptyCheckbox);
        }
    }


    @Override
    public int getCount() {
        return listOfTodo.size();
    }

    @Override
    public Object getItem(int position) {
        if (listOfTodo != null)
            return listOfTodo.get(position);
        else
            return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View todoLayout = inflater.inflate(R.layout.current_todo, parent, false);

        Button addTodo = (Button) todoLayout.findViewById(R.id.addTodo);
        Button delTodo = (Button) todoLayout.findViewById(R.id.deleteTodo);
        todoCheckbox = (CheckBox) todoLayout.findViewById(R.id.todoCheckBox);

        final EditText todoText = (EditText) todoLayout.findViewById(R.id.todoText);
        // set visibility for add button
        if (position == (listOfTodo.size() - 1)) {  // the last of list
            todoText.setVisibility(View.VISIBLE);
            addTodo.setVisibility(View.VISIBLE);
            todoText.setText(listOfTodo.get(position).getText());
            todoCheckbox.setChecked(listOfTodo.get(position).isChecked());

            if (listOfTodo.get(position).getText() != null) {
                todoCheckbox.setVisibility(View.GONE);
            }
        } else if (position != (listOfTodo.size() - 1)) { //others not last
            addTodo.setVisibility(View.INVISIBLE);
        }

        todoText.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(todoText, InputMethodManager.SHOW_IMPLICIT);


        if (listOfTodo.size() != 0) {
            todoCheckbox.setText(listOfTodo.get(position).getText());
            todoCheckbox.setChecked(listOfTodo.get(position).isChecked());
        }
        addTodo.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           CheckBox currTodoCheckbox = new CheckBox(context);
                                           String currTodo = todoText.getText().toString();

                                           currTodoCheckbox.setText(currTodo);
                                           currTodoCheckbox.setChecked(todoCheckbox.isChecked());
                                           listOfTodo.set(position, currTodoCheckbox);

                                           CheckBox emptyCheckbox = new CheckBox(context);
                                           emptyCheckbox.setText("");
                                           listOfTodo.add(emptyCheckbox);
                                           notifyDataSetChanged();
                                       }
                                   }
        );
        delTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listOfTodo.size() == 1) {
//
                    CheckBox emptyCheckbox = new CheckBox(context);
                    emptyCheckbox.setText("");
                    emptyCheckbox.setChecked(false);
                    listOfTodo.set(0, emptyCheckbox);
                    Switch wantTodo = (Switch) currNoteTab.getActivity().findViewById(R.id.wantTodo);
                    wantTodo.setChecked(false);
                } else {
                    listOfTodo.remove(position);
                }


                notifyDataSetChanged();
            }
        });
        todoCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String text = listOfTodo.get(position).getText().toString();
                CheckBox ch = new CheckBox(context);
                ch.setChecked(buttonView.isChecked());
                ch.setText(text);

                listOfTodo.set(position, ch);
                notifyDataSetChanged();
            }
        });


        return todoLayout;
    }
}

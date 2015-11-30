package com.chudolab.remembereverything.type_of_notes;

import android.widget.CheckBox;

/**
 * Created by Chudo on 29.11.2015.
 */
public class CurrentTodoNote  {

    private Boolean isCheched;
    private String todoText;
    private CheckBox checkBox;

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public CurrentTodoNote(CheckBox checkBox) {

        this.checkBox = checkBox;
    }

    public Boolean getIsCheched() {
        return isCheched;
    }

    public void setIsCheched(Boolean isCheched) {
        this.isCheched = isCheched;
    }

    public String getTodoText() {
        return todoText;
    }

    public void setTodoText(String todoText) {
        this.todoText = todoText;
    }

    public CurrentTodoNote(Boolean isCheched, String todoText) {

        this.isCheched = isCheched;
        this.todoText = todoText;
    }
}

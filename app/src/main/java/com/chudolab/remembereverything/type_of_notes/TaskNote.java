package com.chudolab.remembereverything.type_of_notes;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ASUS on 10.11.2015.
 */
public class TaskNote extends Note {
    private Date date;

    public TaskNote(String id, Date dateOfApdate, Date dateOfcreate, String name, String text, Date date) {
        super(id, dateOfcreate, dateOfApdate, name, text);
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String getDescription() {
        return "" + this.getDate().getTime();
    }
}
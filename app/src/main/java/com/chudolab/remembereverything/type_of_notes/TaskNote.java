package com.chudolab.remembereverything.type_of_notes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ASUS on 10.11.2015.
 */
public class TaskNote extends Note {
    private List date;
    private List time;

    public List getTime() {
        return time;
    }

    public void setTime(ArrayList time) {
        this.time = time;
    }

    public TaskNote(String id, Date dateOfApdate, Date dateOfcreate, String name, String text, List date,List time) {
        super(id, dateOfcreate, dateOfApdate, name, text);
        this.date = date;
        this.time=time;
    }

    public List getDate() {
        return date;
    }

    public void setDate(ArrayList date) {
        this.date = date;
    }

    @Override
    public String getDescription() {
        return "date: " + this.getDate().get(0)+"."+this.getDate().get(1)+" time: "+this.getTime().get(0)+"."+this.getTime().get(1);
    }
}
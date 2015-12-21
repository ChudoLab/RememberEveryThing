package com.chudolab.remembereverything.type_of_notes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ASUS on 10.11.2015.
 */
public class ToDoNote extends Note {
    private int period;
    //private ArrayList<String> doing;
    private ArrayList<String> doing;
    private ArrayList<Boolean> doingBoolean;



    public ToDoNote(String id, Date dateOfApdate, Date dateOfcreate, String name,String subject, String text, int period, ArrayList doing,ArrayList doingBoolean) {
        super(id, dateOfApdate, dateOfcreate, name, subject,text);
        this.period = period;
        this.doing = doing;
        this.doingBoolean=doingBoolean;

    }

    public ArrayList<Boolean> getDoingBoolean() {
        return doingBoolean;
    }

    public void setDoingBoolean(ArrayList<Boolean> doingBoolean) {
        this.doingBoolean = doingBoolean;
    }
    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public ArrayList<String> getDoing() {
        return doing;
    }

    public void setDoing(ArrayList<String> doing) {
        this.doing = doing;
    }

}

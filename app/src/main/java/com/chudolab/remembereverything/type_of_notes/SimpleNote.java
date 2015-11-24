package com.chudolab.remembereverything.type_of_notes;

import java.util.Date;

/**
 * Created by ASUS on 10.11.2015.
 */
public class SimpleNote extends Note {
    private String subject;

    public SimpleNote(String id,Date dateOfApdate, Date dateOfcreate,String name, String text,String subject) {
        super(id,dateOfcreate,dateOfApdate,name, text);
        this.subject=subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String getDescription() {
        return this.getSubject();
    }
}

package com.chudolab.remembereverything.type_of_notes;

import java.util.Date;

/**
 * Created by ASUS on 10.11.2015.
 */
abstract public class Note {
    private String id;
    private String name;
    private String text;
    private Date dateOfApdate;
    private Date dateOfCreate;


    public Note(String id, Date dateOfApdate, Date dateOfCreate, String name, String text) {
        this.id = id;
        this.dateOfApdate = dateOfApdate;
        this.dateOfCreate = dateOfCreate;
        this.name = name;
        this.text = text;
    }

    public Note(String name, String text) {
//this.id = id;auto
        this.name = name;
        this.text = text;

    }

    public Note(String text) {
//this.id = id;auto
//this.name = name;auto
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateOfApdate() {
        return dateOfApdate;
    }

    public void setDateOfApdate(Date dateOfCreation) {
        this.dateOfApdate = dateOfCreation;
    }

    public abstract String getDescription();
}

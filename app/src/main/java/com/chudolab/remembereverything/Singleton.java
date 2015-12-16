package com.chudolab.remembereverything;

import com.chudolab.remembereverything.type_of_notes.Note;

import java.util.ArrayList;

/**
 * Created by ASUS on 11.11.2015.
 */
public class Singleton {
    private ArrayList<Note> simpleNotes;
    private ArrayList<Note> toDoNotes;
    private ArrayList<Note> taskNotes;
    private ArrayList<String> subjects;

    private static Singleton instance;

    private Singleton( ) {
        this.simpleNotes = new ArrayList<>();
        this.taskNotes = new ArrayList<>();
        this.toDoNotes = new ArrayList<>();
        this.subjects = new ArrayList<>();
            subjects.add("Thoughts"); // default topic
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public ArrayList<Note> getSimpleNotes() {
        return simpleNotes;
    }

    public ArrayList<Note> getToDoNotes() {
        return taskNotes;
    }

    public ArrayList<Note> getTaskNotes() {
        return toDoNotes;
    }

    public ArrayList<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<String> subjects) {
        this.subjects = subjects;
    }

    public void refresh(ArrayList<Note> list) {
        if (!list.isEmpty()) {
            list.clear();
        }
    }
}

package com.chudolab.remembereverything.type_of_notes;

import java.util.Date;

/**
 * Created by ASUS on 10.11.2015.
 */
public class SimpleNote extends Note {

    public SimpleNote(String id,Date dateOfApdate, Date dateOfcreate,String name, String text,String subject) {
        super(id,dateOfcreate,dateOfApdate,name, subject,text);

    }


}

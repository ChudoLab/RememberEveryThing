package com.chudolab.remembereverything;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

/**
 * Created by Chudo on 15.11.2015.
 */
public class MyApp extends Application {
    private static final String APPLICATION_ID = "add your parse";
    private static final String CLIENT_KEY = "add your parse";
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, APPLICATION_ID ,CLIENT_KEY);
        ParseUser.enableAutomaticUser();
        ParseACL defauAcl = new ParseACL();

        defauAcl.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defauAcl, true);
    }
}

package com.chudolab.remembereverything;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Chudo on 15.11.2015.
 */
public class MyApp extends Application {
    private static final String APPLICATION_ID = "wRLGO7Ec8KuPm0bhO5KBG6AfSNUQdsjTD8BRkQKi";
    private static final String CLIENT_KEY = "TgE4qr2s0fzNrFdWK1mfW4jQpeip4xoB3gbfdsV4";
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, APPLICATION_ID ,CLIENT_KEY);
    }
}

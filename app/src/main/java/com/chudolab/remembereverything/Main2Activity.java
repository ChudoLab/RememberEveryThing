package com.chudolab.remembereverything;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chudolab.remembereverything.main_page.MainPageActivity;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())){
            Intent intent = new Intent(Main2Activity.this,LoginSingupActivity.class);
            startActivity(intent);
            finish();

        }else {
            ParseUser currentUser = ParseUser.getCurrentUser();
            if(currentUser!=null){
                Intent intent = new Intent(Main2Activity.this, MainPageActivity.class);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(Main2Activity.this,LoginSingupActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}



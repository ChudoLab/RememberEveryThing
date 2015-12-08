package com.chudolab.remembereverything;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chudolab.remembereverything.main_page.MainPageActivity;
import com.parse.LogInCallback;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginSingupActivity extends AppCompatActivity {

    Button singIn;
    Button logIn;

    EditText name;
    EditText pass;

    String userName;
    String userPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        singIn = (Button) findViewById(R.id.singIn);
        logIn=(Button) findViewById(R.id.logIn);
        name = (EditText) findViewById(R.id.name);

        pass = (EditText) findViewById(R.id.pass);
        userName = name.getText().toString();
        userPass=pass.getText().toString();

        final Intent intent = new Intent(getApplicationContext(),MainPageActivity.class);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass = (EditText) findViewById(R.id.pass);
                userName = name.getText().toString();
                userPass = pass.getText().toString();
                ParseUser.logInInBackground(userName, userPass, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, com.parse.ParseException e) {
                        if (parseUser != null) {
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "This user doesn't exist ", Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });
        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = name.getText().toString();
                userPass = pass.getText().toString();
                if (userName.equals("") && userPass.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please complete the sin up form", Toast.LENGTH_LONG).show();
                } else {
                    ParseUser user = new ParseUser();
                    user.setUsername(userName);
                    user.setPassword(userPass);
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                            if (e == null) {
                                Toast.makeText(getApplicationContext(), "Successful sing up!", Toast.LENGTH_LONG).show();
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Sing up error: " + e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
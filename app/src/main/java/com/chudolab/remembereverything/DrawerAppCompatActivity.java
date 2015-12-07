package com.chudolab.remembereverything;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.chudolab.remembereverything.lists_of_notes.SimpleListActivity;
import com.chudolab.remembereverything.lists_of_notes.TasksListActivity;
import com.chudolab.remembereverything.lists_of_notes.ToDoListActivity;
import com.chudolab.remembereverything.main_page.MainPageActivity;

/**
 * Created by Chudo on 02.12.2015.
 */
public abstract class DrawerAppCompatActivity extends AppCompatActivity {
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        //Toolbar
        Toolbar myToolBar = getToolbar();
        setSupportActionBar(myToolBar);

        //DRAWER
        //Toggle Drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                myToolBar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(toggle);

        //Drawer job with keyboard

        drawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //how to hide a keyboard
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });


    }

    public abstract Toolbar getToolbar();

    public abstract View getContentView();

    public abstract int getToolbarMenu();


    //drawer menu click
    public void onDrawerMenuClick(MenuItem menuItem) {
        Intent intent;
        if (menuItem.getItemId() == R.id.buttonCurrentNote) {
            //TODO взять из бекстека мейн и показать
            intent = new Intent(getApplicationContext(), MainPageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Intent.FLAG_ACTIVITY_CLEAR_TOP |
            startActivity(intent);

//            finish();
        } else {
            if (menuItem.getItemId() == R.id.buttonSimpleNotes) {
                intent = new Intent(getApplicationContext(), SimpleListActivity.class);
            } else if (menuItem.getItemId() == R.id.buttonTodos) {
                intent = new Intent(getApplicationContext(), ToDoListActivity.class);
            } else {
                intent = new Intent(getApplicationContext(), TasksListActivity.class);
            }

            startActivity(intent);
            finish();

        }


    }

    //toolbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(getToolbarMenu(), menu);
        return true;
    }

    // this is for all menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
//        if(item.getItemId() == R.id.back){
//            onBackPressed();
//        }
//        if (item.getItemId() == R.id.goHome) {
//            finish();
//        }
//          else if (item.getItemId() == R.id.currentOptions) {
//            Intent intent = new Intent(getApplicationContext(), Options.class);
//            startActivityForResult(intent, 1);
//        }

        return super.onOptionsItemSelected(item);
    }

    // this 2 methods are for Drawer toggle
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

}

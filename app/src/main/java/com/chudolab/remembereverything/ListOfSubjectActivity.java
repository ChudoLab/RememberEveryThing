package com.chudolab.remembereverything;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chudolab.remembereverything.lists_of_notes.SimpleListActivity;

public class ListOfSubjectActivity extends AppCompatActivity {

    String chooseSubject;
    ListView lvSubjects;

    @Override
    protected void onStart() {
        super.onStart();
        lvSubjects.invalidateViews();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_subject);
        lvSubjects = (ListView) findViewById(R.id.lvSubjects);
        SubjectAdapter adapter = new SubjectAdapter(getApplicationContext());
        lvSubjects.setAdapter(adapter);
//adding topics
        Singleton.getInstance().getSubjects().removeAll(Singleton.getInstance().getSubjects());
        Singleton.getInstance().getSubjects().add("Shopping");
        Singleton.getInstance().getSubjects().add("Thoughts");
        Singleton.getInstance().getSubjects().add("super");
        lvSubjects.invalidateViews();
        lvSubjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chooseSubject = Singleton.getInstance().getSubjects().get(position);
                Intent intent = new Intent(getApplicationContext(), SimpleListActivity.class);
                intent.putExtra("subject", chooseSubject);
                startActivity(intent);
            }
        });
    }
}

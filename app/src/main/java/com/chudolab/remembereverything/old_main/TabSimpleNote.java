//package com.chudolab.remembereverything.old_main;
//
///**
// * Created by Chudo on 13.11.2015.
// */
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.Switch;
//import android.widget.Toast;
//
//import com.chudolab.remembereverything.R;
//import com.chudolab.remembereverything.Singleton;
//
//import java.util.ArrayList;
//
//public class TabSimpleNote extends Fragment {
//    ArrayList<String> existingTopics;
//    final static int RESULT_SIMPLE_NOTE = 1;
//    final static int RESULT_SIMPLE_TODO = 2;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        final View v = inflater.inflate(R.layout.tab_simple_note, container, false);
//
//        existingTopics = Singleton.getInstance().getSubjects();
//
//        final ArrayAdapter<String> topicAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, existingTopics);
//        topicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        final Spinner spinner = (Spinner) v.findViewById(R.id.existingTopics);
//        spinner.setAdapter(topicAdapter);
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//
//        });
//        Button okAndGo = (Button) v.findViewById(R.id.buttonOkAndGo);
//        okAndGo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EditText newName = (EditText) v.findViewById(R.id.addName);
//                EditText newTopic = (EditText) v.findViewById(R.id.addTopic);
//                Switch makeTodo = (Switch) v.findViewById(R.id.wantTodo);
//
//                String newNameResult = newName.getText().toString();
//                String newTopicResult = newTopic.getText().toString();
//                Boolean makeTodoResult = makeTodo.isChecked();
//
//                Log.e("newTopic Res", newTopicResult);
//                Log.e("size befor", "" + existingTopics.size());
//
//                if(existingTopics.size()!=0) {
//                    for (int i = 0; i < existingTopics.size(); i++) {
//
//                        if (existingTopics.get(i).equals(newTopicResult) != true) {
//                            existingTopics.add(newTopicResult);
//                            Log.e("added", newTopicResult);
//                            Singleton.getInstance().setSubjects(existingTopics);
//                            topicAdapter.notifyDataSetChanged();
//                        } else {
//                            Log.e("equal?", "" + existingTopics.get(i).equals(newTopicResult));
//                        }
//                    }
//                }
//                else  {
//                    existingTopics.add(newTopicResult);
//                    Log.e("added", newTopicResult);
//                    Singleton.getInstance().setSubjects(existingTopics);
//                    topicAdapter.notifyDataSetChanged();
//                }
//                Log.e("size", "" + existingTopics.size());
//
//                ArrayList<String> optionsSelected = new ArrayList<>();
//
//                if (!newNameResult.isEmpty()) {
//                    optionsSelected.add(newNameResult);
//                } else {
//                    optionsSelected.add("No name");
//                }
//                if (!newTopicResult.isEmpty()) {
//                    optionsSelected.add(newTopicResult);
//                } else {
//                    optionsSelected.add(topicAdapter.getItem(spinner.getSelectedItemPosition()));
//                }
//
//                Intent intent = new Intent();
//
//                if (!makeTodoResult) {
//                    getActivity().setResult(RESULT_SIMPLE_NOTE, intent);
//                } else {
//                    getActivity().setResult(RESULT_SIMPLE_TODO, intent);
//                }
//
//                intent.putStringArrayListExtra("simpleOptions", optionsSelected);
//                getActivity().setIntent(intent);
//
//                getActivity().finish();
//            }
//
//        });
//        return v;
//    }
//
//
//}
//

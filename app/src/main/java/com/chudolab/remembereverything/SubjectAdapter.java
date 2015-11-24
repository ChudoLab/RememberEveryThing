package com.chudolab.remembereverything;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Chudo on 23.11.2015.
 */
public class SubjectAdapter extends BaseAdapter {
    Context context;

    public SubjectAdapter(Context context) {
        this.context=context;

    }

    @Override
    public int getCount() {
        return Singleton.getInstance().getSubjects().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tvSubject = new TextView(context);
        tvSubject.setText(Singleton.getInstance().getSubjects().get(position));
        tvSubject.setTextColor(Color.BLACK);
        return tvSubject;
    }
}

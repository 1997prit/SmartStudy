package com.example.hp.smartstudy;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by HP on 17-02-2017.
 */

public class QuizAdapter extends BaseAdapter {
    Activity a;
    ArrayList<HashMap<String,String>> arrayList;
    LayoutInflater layoutInflater;

    QuizAdapter (Activity a,ArrayList<HashMap<String,String>>arrayList){
        this.a=a;
        this.arrayList=arrayList;
        layoutInflater=a.getLayoutInflater();
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null) {
            convertView = layoutInflater.inflate(R.layout.raw_quiz, null);
        }
        TextView tvquiz=(TextView)convertView.findViewById(R.id.tvquiz);
        HashMap<String,String> hashMap=arrayList.get(position);
        tvquiz.setText(hashMap.get(QuizActivity.TAG_QUIZ_TITLE));
        return convertView;

    }
}

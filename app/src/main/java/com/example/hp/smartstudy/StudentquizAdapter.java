package com.example.hp.smartstudy;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by HP on 17-02-2017.
 */

public class StudentquizAdapter extends BaseAdapter {
    Activity a;
    ArrayList<HashMap<String, String>> arrayList;
    LayoutInflater layoutInflater;

    StudentquizAdapter(Activity a, ArrayList<HashMap<String, String>> arrayList) {
        this.a = a;
        this.arrayList = arrayList;
        layoutInflater = a.getLayoutInflater();
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
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.raw_studentquiz, null);
        }

        TextView tvtotalcorrect = (TextView) convertView.findViewById(R.id.tvstudenttotalcorrect);
        TextView tvtotalattempt = (TextView) convertView.findViewById(R.id.tvstudenttotalattempt);
        TextView tvquizdate = (TextView) convertView.findViewById(R.id.tvstudentquizdate);
        TextView tvstudent = (TextView) convertView.findViewById(R.id.tvstudentquizstudent);
        TextView tvquiz = (TextView) convertView.findViewById(R.id.tvstudentquizquiz);


        HashMap<String, String> hashMap = arrayList.get(position);
        tvtotalcorrect.setText("Total Correct Answer :"+hashMap.get(StudentquizActivity.TAG_TOTAL_CORRECT));
        tvtotalattempt.setText("Total Attempt Question :"+hashMap.get(StudentquizActivity.TAG_TOTAL_ATTAEMPT));
        tvquizdate.setText("Quiz Date :"+hashMap.get(StudentquizActivity.TAG_QUIZ_DATE));

        tvstudent.setText("Student Name :"+hashMap.get(StudentquizActivity.TAG_STUDENT_ID));
        tvquiz.setText("Quiz Name :"+hashMap.get(StudentquizActivity.TAG_QUIZ_ID));
        return convertView;







    }
}

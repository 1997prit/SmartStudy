package com.example.hp.smartstudy;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by HP on 16-02-2017.
 */

public class FacultyAdapter extends BaseAdapter {
    Activity a;
    ArrayList<HashMap<String, String>> arrayList;
    LayoutInflater layoutInflater;

    FacultyAdapter(Activity a, ArrayList<HashMap<String, String>> arrayList) {
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
            convertView = layoutInflater.inflate(R.layout.raw_faculty, null);
        }
        TextView tvfaculty = (TextView) convertView.findViewById(R.id.tvfaculty);
        TextView tvfacultyQualification = (TextView) convertView.findViewById(R.id.tvfacultyQualification);
        TextView tvfacultySchool = (TextView) convertView.findViewById(R.id.tvfacultySchool);
        HashMap<String, String> hashMap = arrayList.get(position);
        tvfaculty.setText(hashMap.get(FacultyActivity.TAG_FACULTY_NAME));
        tvfacultyQualification.setText("Qualification:" + hashMap.get(FacultyActivity.TAG_FACULTY_QUALIFICATION));
        tvfacultySchool.setText("School:" + hashMap.get(FacultyActivity.TAG_SCHOOL_ID));
        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
        Picasso.with(a).load(jsonParser.FACULTY_IMAGE_URL + hashMap.get(FacultyActivity.TAG_FACULTY_PHOTO).replace(" ", "%20")).into(ivImage);
        return convertView;
    }
}

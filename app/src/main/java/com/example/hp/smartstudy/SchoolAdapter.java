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

public class SchoolAdapter extends BaseAdapter {
    Activity a;
    ArrayList<HashMap<String,String>> arrayList;
    LayoutInflater layoutInflater;

    SchoolAdapter (Activity a,ArrayList<HashMap<String,String>>arrayList){
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
            convertView = layoutInflater.inflate(R.layout.raw_school, null);
        }
        TextView tvschool=(TextView)convertView.findViewById(R.id.tvschool);
        HashMap<String,String> hashMap=arrayList.get(position);
        tvschool.setText(hashMap.get(SchoolActivity.TAG_SCHOOL_NAME));
        return convertView;
    }
}

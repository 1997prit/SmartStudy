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

public class ChatAdapter extends BaseAdapter {
    Activity a;
    ArrayList<HashMap<String, String>> arrayList;
    LayoutInflater layoutInflater;

    ChatAdapter(Activity a, ArrayList<HashMap<String, String>> arrayList) {
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
            convertView = layoutInflater.inflate(R.layout.raw_chat, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.tvMsg);
        TextView textView1 = (TextView) convertView.findViewById(R.id.tvdate);
        TextView textView2 = (TextView) convertView.findViewById(R.id.tvSender);
        ImageView iv = (ImageView) convertView.findViewById(R.id.ivChat);
        HashMap<String, String> hashMap = arrayList.get(position);

        textView.setText(hashMap.get(ChatActivity.TAG_CHAT_DESCRIPTION));
        textView1.setText(hashMap.get(ChatActivity.TAG_CHAT_DATE));
        if (hashMap.get(ChatActivity.TAG_SENDER).equals("faculty")) {
            Picasso.with(a).load(jsonParser.FACULTY_IMAGE_URL + a.getIntent().getStringExtra(FacultyActivity.TAG_FACULTY_PHOTO).replace(" ", "%20"));
            textView2.setText(hashMap.get(ChatActivity.TAG_FACULTY_ID));
        } else {
            textView2.setText(hashMap.get(ChatActivity.TAG_STUDENT_ID));
        }
        return convertView;
    }
}

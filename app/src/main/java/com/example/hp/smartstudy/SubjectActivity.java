package com.example.hp.smartstudy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterViewAnimator;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SubjectActivity extends AppCompatActivity {
    ListView lvsubject;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    public static final String TAG = "subject";
    public static final String TAG_SUBJECT_ID = "subject_id";
    public static final String TAG_SUBJECT_NAME = "subject_name";
    public static final String TAG_CLASS_ID = "class_id";

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        getSupportActionBar().setTitle("Choose Subject");
        lvsubject = (ListView) findViewById(R.id.lvsubject);
        new getSubject().execute();
    }

    class getSubject extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(SubjectActivity.this);
            progressDialog.setMessage("Loading Please Wait for some time....");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String json = jsonParser.invokeJSON("getSubject", "class_id", getIntent().getStringExtra(ClassActivity.TAG_CLASS_ID));
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(TAG_SUBJECT_ID, jsonObject1.getString(TAG_SUBJECT_ID));
                    hashMap.put(TAG_SUBJECT_NAME, jsonObject1.getString(TAG_SUBJECT_NAME));
                    hashMap.put(TAG_CLASS_ID, jsonObject1.getString(TAG_CLASS_ID));
                    arrayList.add(hashMap);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            SubjectAdapter subjectAdapter = new SubjectAdapter(SubjectActivity.this, arrayList);
            lvsubject.setAdapter(subjectAdapter);
            lvsubject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SharedPreferences.Editor e = getSharedPreferences("MyFile", 0).edit();
                    e.putString(TAG_SUBJECT_ID, arrayList.get(position).get(TAG_SUBJECT_ID));
                    e.commit();
                    startActivity(new Intent(SubjectActivity.this, MaterialtypeActivity.class));
                }
            });
            progressDialog.dismiss();
        }
    }
}

package com.example.hp.smartstudy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FacultyActivity extends AppCompatActivity {
    ListView lvfaculty;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    public static final String TAG = "faculty";
    public static final String TAG_FACULTY_ID = "faculty_id";
    public static final String TAG_FACULTY_NAME = "faculty_name";
    public static final String TAG_FACULTY_ADDRESS = "faculty_address";
    public static final String TAG_FACULTY_MOBILE = "faculty_mobile";
    public static final String TAG_FACULTY_PHOTO = "faculty_photo";
    public static final String TAG_FACULTY_GENDER = "faculty_gender";
    public static final String TAG_FACULTY_PASSWORD = "faculty_password";
    public static final String TAG_FACULTY_QUALIFICATION = "faculty_qualification";
    public static final String TAG_SCHOOL_ID = "school_id";
    public static final String TAG_AREA_ID = "area_id";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);
        lvfaculty = (ListView) findViewById(R.id.lvfaculty);
        new getFaculty().execute();
    }

    class getFaculty extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(FacultyActivity.this);
            progressDialog.setMessage("Loading Please Wait for some time....");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String json = jsonParser.invokeJSON("getFaculty");
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(TAG_FACULTY_ID, jsonObject1.getString(TAG_FACULTY_ID));
                    hashMap.put(TAG_FACULTY_NAME, jsonObject1.getString(TAG_FACULTY_NAME));
                    hashMap.put(TAG_FACULTY_ADDRESS, jsonObject1.getString(TAG_FACULTY_ADDRESS));
                    hashMap.put(TAG_FACULTY_MOBILE, jsonObject1.getString(TAG_FACULTY_MOBILE));
                    hashMap.put(TAG_FACULTY_PHOTO, jsonObject1.getString(TAG_FACULTY_PHOTO));
                    hashMap.put(TAG_FACULTY_GENDER, jsonObject1.getString(TAG_FACULTY_GENDER));
                    hashMap.put(TAG_FACULTY_PASSWORD, jsonObject1.getString(TAG_FACULTY_PASSWORD));
                    hashMap.put(TAG_FACULTY_QUALIFICATION, jsonObject1.getString(TAG_FACULTY_QUALIFICATION));
                    hashMap.put(TAG_SCHOOL_ID, jsonObject1.getString(TAG_SCHOOL_ID));
                    hashMap.put(TAG_AREA_ID, jsonObject1.getString(TAG_AREA_ID));
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
            FacultyAdapter facultyAdapter = new FacultyAdapter(FacultyActivity.this, arrayList);
            lvfaculty.setAdapter(facultyAdapter);
            lvfaculty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent i1 = new Intent(FacultyActivity.this, ChatActivity.class);
                    i1.putExtra(TAG_FACULTY_ID, arrayList.get(i).get(TAG_FACULTY_ID));
                    i1.putExtra(TAG_FACULTY_NAME, arrayList.get(i).get(TAG_FACULTY_NAME));
                    i1.putExtra(TAG_FACULTY_PHOTO, arrayList.get(i).get(TAG_FACULTY_PHOTO));
                    startActivity(i1);
                }
            });
            progressDialog.dismiss();
        }
    }
}

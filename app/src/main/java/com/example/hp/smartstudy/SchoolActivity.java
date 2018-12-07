package com.example.hp.smartstudy;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SchoolActivity extends AppCompatActivity {
    ListView lvschool;
    ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();
    public static final String TAG="school";
    public static final String TAG_SCHOOL_ID="school_id";
    public static final String TAG_SCHOOL_NAME="school_name";
    public static final String TAG_SCHOOL_ADDRESS="school_address";
    public static final String TAG_AREA_ID="area_id";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);
        lvschool=(ListView)findViewById(R.id.lvschool);
        new getSchool().execute();
    }
    class getSchool extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(SchoolActivity.this);
            progressDialog.setMessage("Loading Please Wait for some time....");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String json= jsonParser.invokeJSON("getSchool");
            try {
                JSONObject jsonObject=new JSONObject(json);
                JSONArray jsonArray=jsonObject.getJSONArray(TAG);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                    HashMap<String,String>hashMap=new HashMap<>();
                    hashMap.put(TAG_SCHOOL_ID,jsonObject1.getString(TAG_SCHOOL_ID));
                    hashMap.put(TAG_SCHOOL_NAME,jsonObject1.getString(TAG_SCHOOL_NAME));
                    hashMap.put(TAG_SCHOOL_ADDRESS,jsonObject1.getString(TAG_SCHOOL_ADDRESS));
                    hashMap.put(TAG_AREA_ID,jsonObject1.getString(TAG_AREA_ID));
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
            SchoolAdapter schoolAdapter=new SchoolAdapter(SchoolActivity.this,arrayList);
            lvschool.setAdapter(schoolAdapter);
            progressDialog.dismiss();

        }
    }
}

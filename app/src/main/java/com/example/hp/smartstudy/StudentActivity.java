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

public class StudentActivity extends AppCompatActivity {
    ListView lvstudent;
    ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();
    public static final String TAG="student";
    public static final String TAG_STUDENT_ID="student_id";
    public static final String TAG_STUDENT_NAME="student_name";
    public static final String TAG_STUDENT_ADDRESS="student_address";
    public static final String TAG_STUDENT_MOBILE="student_mobile";
    public static final String TAG_STUDENT_MAIL="student_mail";
    public static final String TAG_STUDENT_PASSWORD="student_password";
    public static final String TAG_STUDENT_GENDER="student_gender";
    public static final String  TAG_CLASS_ID="class_id";
    public static final String TAG_BOARD_ID="board_id";
    public static final String TAG_SCHOOL_ID="school_id";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        lvstudent=(ListView)findViewById(R.id.lvstudent);
        new getStudent().execute();
    }
    class getStudent extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(StudentActivity.this);
            progressDialog.setMessage("Loading Please Wait for some time....");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String json= jsonParser.invokeJSON("getStudent");
            try {
                JSONObject jsonObject=new JSONObject(json);
                JSONArray jsonArray=jsonObject.getJSONArray(TAG);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                    HashMap<String,String>hashMap=new HashMap<>();
                    hashMap.put(TAG_STUDENT_ID,jsonObject1.getString(TAG_STUDENT_ID));
                    hashMap.put(TAG_STUDENT_NAME,jsonObject1.getString(TAG_STUDENT_NAME));
                    hashMap.put(TAG_STUDENT_ADDRESS,jsonObject1.getString(TAG_STUDENT_ADDRESS));
                    hashMap.put(TAG_STUDENT_MOBILE,jsonObject1.getString(TAG_STUDENT_MOBILE));
                    hashMap.put(TAG_STUDENT_MAIL,jsonObject1.getString(TAG_STUDENT_MAIL));
                    hashMap.put(TAG_STUDENT_PASSWORD,jsonObject1.getString(TAG_STUDENT_PASSWORD));
                    hashMap.put(TAG_STUDENT_GENDER,jsonObject1.getString(TAG_STUDENT_GENDER));
                    hashMap.put(TAG_CLASS_ID,jsonObject1.getString(TAG_CLASS_ID));
                    hashMap.put(TAG_BOARD_ID,jsonObject1.getString(TAG_BOARD_ID));
                    hashMap.put(TAG_SCHOOL_ID,jsonObject1.getString(TAG_SCHOOL_ID));
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
            StudentAdapter studentAdapter=new StudentAdapter(StudentActivity.this,arrayList);
            lvstudent.setAdapter(studentAdapter);
            progressDialog.dismiss();

        }
    }
}

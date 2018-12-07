package com.example.hp.smartstudy;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class StudentquizActivity extends AppCompatActivity {
    ListView lvstudentquiz;
    TextView tvtotalcorrect,tvtotalattempt,tvquizdate,tvquizresult,tvstudent,tvquiz;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    public static final String TAG = "student_quiz";
    public static final String TAG_STUDENT_QUIZ_ID = "student_quiz_id";
    public static final String TAG_TOTAL_ATTAEMPT = "total_attempt";
    public static final String TAG_TOTAL_CORRECT = "total_correct";
    public static final String TAG_QUIZ_DATE = "quiz_date";
    public static final String TAG_STUDENT_ID = "student_id";
    public static final String TAG_QUIZ_ID = "quiz_id";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentquiz);
        initialize();
        new getStudentquiz().execute();
    }
    void initialize()
    {
        lvstudentquiz = (ListView) findViewById(R.id.lvstudentquiz);
        tvtotalcorrect=(TextView) findViewById(R.id.tvstudenttotalcorrect);
        tvtotalattempt=(TextView) findViewById(R.id.tvstudenttotalattempt);
        tvquizdate=(TextView) findViewById(R.id.tvstudentquizdate);
        tvstudent=(TextView) findViewById(R.id.tvstudentquizstudent);
        tvquiz=(TextView) findViewById(R.id.tvstudentquizquiz);
    }
    class getStudentquiz extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(StudentquizActivity.this);
            progressDialog.setMessage("Loading Please Wait for some time....");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            String json = jsonParser.invokeJSON("getStudent_quiz", "Student_id", getSharedPreferences("MyFile", 0).getString(LoginActivity.TAG_STUDENT_ID, ""));
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(TAG_STUDENT_QUIZ_ID, jsonObject1.getString(TAG_STUDENT_QUIZ_ID));
                    hashMap.put(TAG_TOTAL_ATTAEMPT, jsonObject1.getString(TAG_TOTAL_ATTAEMPT));
                    hashMap.put(TAG_TOTAL_CORRECT, jsonObject1.getString(TAG_TOTAL_CORRECT));
                    hashMap.put(TAG_QUIZ_DATE, jsonObject1.getString(TAG_QUIZ_DATE));
                    hashMap.put(TAG_STUDENT_ID, jsonObject1.getString(TAG_STUDENT_ID));
                    hashMap.put(TAG_QUIZ_ID, jsonObject1.getString(TAG_QUIZ_ID));
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

            StudentquizAdapter studentquizAdapter = new StudentquizAdapter(StudentquizActivity.this, arrayList);
            lvstudentquiz.setAdapter(studentquizAdapter);
            progressDialog.dismiss();

        }
    }
}

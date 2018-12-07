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

public class QuizActivity extends AppCompatActivity {
    ListView lvquiz;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    public static final String TAG = "quiz";
    public static final String TAG_QUIZ_ID = "quiz_id";
    public static final String TAG_QUIZ_TITLE = "quiz_title";
    public static final String TAG_QUIZ_DESCRIPTION = "quiz_description";
    public static final String TAG_QUIZ_DATE = "quiz_date";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        lvquiz = (ListView) findViewById(R.id.lvquiz);
        new getQuiz().execute();
    }

    class getQuiz extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(QuizActivity.this);
            progressDialog.setMessage("Loading Please Wait for some time....");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String json = jsonParser.invokeJSON("getQuiz", "Student_id", getSharedPreferences("MyFile", 0).getString(LoginActivity.TAG_STUDENT_ID, ""));
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(TAG_QUIZ_ID, jsonObject1.getString(TAG_QUIZ_ID));
                    hashMap.put(TAG_QUIZ_TITLE, jsonObject1.getString(TAG_QUIZ_TITLE));
                    hashMap.put(TAG_QUIZ_DESCRIPTION, jsonObject1.getString(TAG_QUIZ_DESCRIPTION));
                    hashMap.put(TAG_QUIZ_DATE, jsonObject1.getString(TAG_QUIZ_DATE));
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
            QuizAdapter quizAdapter = new QuizAdapter(QuizActivity.this, arrayList);
            lvquiz.setAdapter(quizAdapter);
            lvquiz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(QuizActivity.this, QuizquestionActivity.class);
                    i.putExtra(TAG_QUIZ_ID, arrayList.get(position).get(TAG_QUIZ_ID));
                    startActivity(i);
                }
            });
            progressDialog.dismiss();

        }
    }
}

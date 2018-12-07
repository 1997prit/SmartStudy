package com.example.hp.smartstudy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class QuizquestionActivity extends AppCompatActivity {
    ListView lvquizquestion;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    public static final String TAG = "quiz_question";
    public static final String TAG_QUIZZ_QUESTION_ID = "quiz_question_id";
    public static final String TAG_QUIZZ_QUESTION_DESCRIPTION = "quiz_question_description";
    public static final String TAG_OPTION_A = "option_a";
    public static final String TAG_OPTION_B = "option_b";
    public static final String TAG_OPTION_C = "option_c";
    public static final String TAG_OPTION_D = "option_d";
    public static final String TAG_CORRECT_ANSWER = "correct_answer";
    public static final String TAG_QUIZ_ID = "quiz_id";
    ProgressDialog progressDialog;
    Button btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizquestion);
        lvquizquestion = (ListView) findViewById(R.id.lvquizquestion);
        SharedPreferences.Editor e = getSharedPreferences("Quiz", 0).edit();
        e.clear();
        e.commit();
        btnFinish = (Button) findViewById(R.id.btnComplete);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int correct = 0;
                int incorrect = 0;
                int attempt = 0;
                SharedPreferences preferences = getSharedPreferences("Quiz", 0);
                for (int i = 0; i < arrayList.size(); i++) {
                    if (!preferences.getString(arrayList.get(i).get(TAG_QUIZZ_QUESTION_ID), "").equals("")) {
                        attempt++;
                        if (preferences.getString(arrayList.get(i).get(TAG_QUIZZ_QUESTION_ID), "").equals(arrayList.get(i).get(TAG_CORRECT_ANSWER))) {
                            correct++;
                        } else {
                            incorrect++;
                        }
                    }
                }
                Date d1 = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yy");

                new insertQuiz().execute("Insert into [Student_quiz](total_attempt,total_correct,quiz_date,student_id,quiz_id)values('" + attempt + "','" + correct + "','" + simpleDateFormat.format(d1) + "','" + getSharedPreferences("MyFile", 0).getString(LoginActivity.TAG_STUDENT_ID, "") + "','" + getIntent().getStringExtra(QuizActivity.TAG_QUIZ_ID) + "')");
            }
        });
        new getQuizquestion().execute();
    }

    class insertQuiz extends AsyncTask<String, Void, Void> {
        String result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(QuizquestionActivity.this);
            progressDialog.setMessage("Submitting Quiz....");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            String json = jsonParser.invokeJSON("setdata", "query", params[0]);
            try {
                JSONObject jsonObject = new JSONObject(json);
                result = jsonObject.getString("Result");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (result.equals("1")) {
                Toast.makeText(QuizquestionActivity.this, "Quiz Submitted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(QuizquestionActivity.this, StudentquizActivity.class));
                finish();
            } else {
                Toast.makeText(QuizquestionActivity.this, "Error in Submitting Quiz. Try again.", Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        }
    }


    class getQuizquestion extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(QuizquestionActivity.this);
            progressDialog.setMessage("Loading Please Wait for some time....");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String json = jsonParser.invokeJSON("getQuiz_question", "quiz_id", getIntent().getStringExtra(QuizActivity.TAG_QUIZ_ID));
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(TAG_QUIZZ_QUESTION_ID, jsonObject1.getString(TAG_QUIZZ_QUESTION_ID));
                    hashMap.put(TAG_QUIZZ_QUESTION_DESCRIPTION, jsonObject1.getString(TAG_QUIZZ_QUESTION_DESCRIPTION));
                    hashMap.put(TAG_OPTION_A, jsonObject1.getString(TAG_OPTION_A));
                    hashMap.put(TAG_OPTION_B, jsonObject1.getString(TAG_OPTION_B));
                    hashMap.put(TAG_OPTION_C, jsonObject1.getString(TAG_OPTION_C));
                    hashMap.put(TAG_OPTION_D, jsonObject1.getString(TAG_OPTION_D));
                    hashMap.put(TAG_CORRECT_ANSWER, jsonObject1.getString(TAG_CORRECT_ANSWER));
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
            QuizquestionAdapter quizquestionAdapter = new QuizquestionAdapter(QuizquestionActivity.this, arrayList);
            lvquizquestion.setAdapter(quizquestionAdapter);
            progressDialog.dismiss();


        }
    }
}

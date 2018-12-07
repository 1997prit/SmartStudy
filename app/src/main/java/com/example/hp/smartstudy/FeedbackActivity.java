package com.example.hp.smartstudy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {
    ListView lvfeedback;
    EditText etfeedbackdate, etfeedbackdescription;
    Button btnfeedback;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    public static final String TAG = "feedback";
    public static final String TAG_FEEDBACK_ID = "feedback_id";
    public static final String TAG_FEEDBACK_DATE = "feedback_date";
    public static final String TAG_FEEDBACK_DESCRIPTION = "feedback_description";
    public static final String TAG_STUDENT_ID = "student_id";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initialize();
    }

    void initialize() {
        etfeedbackdate = (EditText) findViewById(R.id.etfeedbackdate);
        etfeedbackdescription = (EditText) findViewById(R.id.etfeedbackdescription);
        btnfeedback = (Button) findViewById(R.id.btnfeedbacksubmit);
        btnfeedback.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        new getFeedback().execute();
    }


    class getFeedback extends AsyncTask<Void, Void, Void> {
        String result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(FeedbackActivity.this);
            progressDialog.setMessage("Loading Please Wait for some time....");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.d(TAG, "doInBackground: " + "Insert into Feedback(feedback_date,feedback_description)values('" + etfeedbackdate.getText().toString() + "','" + etfeedbackdescription.getText().toString() + "')");
            String json = jsonParser.invokeJSON("setdata", "query", "Insert into Feedback(feedback_date,feedback_description)values('" + etfeedbackdate.getText().toString() + "','" + etfeedbackdescription.getText().toString() + "')");
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
            try {
                if (result.equals("1")) {
                    Toast.makeText(FeedbackActivity.this, "Thanks For Giving Your Feedback", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(FeedbackActivity.this, HomeActivity.class));
                } else {
                    Toast.makeText(FeedbackActivity.this, "Try Again For Giving Feedback", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            progressDialog.dismiss();
        }
    }

}

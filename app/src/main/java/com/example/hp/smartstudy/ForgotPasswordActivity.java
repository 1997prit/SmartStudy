package com.example.hp.smartstudy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etforgotpasswordmail;
    Button btnstudentforgetpassword;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initialize();
    }

    void initialize() {
        etforgotpasswordmail = (EditText) findViewById(R.id.etforgotpasswordmail);
        btnstudentforgetpassword = (Button) findViewById(R.id.btnstudentforgotpassword);
        btnstudentforgetpassword.setOnClickListener(this);
    }

    class forgotpassword extends AsyncTask<Void, Void, Void> {
        String result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
            progressDialog.setMessage("Loading Please Wait");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String json = jsonParser.invokeJSON("forgotpassword", "query", "Select * from Student where student_mail = '" + etforgotpasswordmail + "'");
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
                Toast.makeText(ForgotPasswordActivity.this, "Mail Send", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ForgotPasswordActivity.this, "Error sending mail", Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.btnstudentforgotpassword:
                new forgotpassword().execute();
                break;
        }
    }
}

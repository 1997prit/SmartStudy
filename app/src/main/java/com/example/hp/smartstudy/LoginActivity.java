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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etusermail, etuserpassword;
    Button btnuserlogin;
    Button btnuserregister;
    Button btnuserforgotpassword;
    SharedPreferences s1;
    ProgressDialog progressDialog;
    public static final String TAG_STUDENT_ID = "student_id";
    public static final String TAG_STUDENT_NAME = "student_name";
    public static final String TAG_STUDENT_PASSWORD = "student_password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initalize();

    }

    void initalize() {
        s1 = getSharedPreferences("MyFile", 0);
        etusermail = (EditText) findViewById(R.id.etuseremail);
        etuserpassword = (EditText) findViewById(R.id.etuserpassword);
        btnuserlogin = (Button) findViewById(R.id.btnlogin);
        btnuserforgotpassword = (Button) findViewById(R.id.btnforgotpassword);
        btnuserregister = (Button) findViewById(R.id.btncreatenewpassword);
        btnuserlogin.setOnClickListener(this);
        btnuserforgotpassword.setOnClickListener(this);
        btnuserregister.setOnClickListener(this);
    }

    class getLogin extends AsyncTask<Void, Void, Void> {
        JSONObject jsonObject;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Loading Please Wait for some time....");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String json = jsonParser.invokeJSON("login", "query", "Select * from Student where student_mail = '" + etusermail.getText().toString() + "' and student_password='" + etuserpassword.getText().toString() + "'");
            try {
                jsonObject = new JSONObject(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                if (!jsonObject.getString(TAG_STUDENT_ID).equals("null")) {
                    SharedPreferences.Editor e = s1.edit();
                    e.putString(TAG_STUDENT_ID, jsonObject.getString(TAG_STUDENT_ID));
                    e.putString(TAG_STUDENT_NAME, jsonObject.getString(TAG_STUDENT_NAME));
                    e.putString(TAG_STUDENT_PASSWORD, jsonObject.getString(TAG_STUDENT_PASSWORD));
                    e.commit();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid Login Details", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressDialog.dismiss();
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnlogin:
                new getLogin().execute();
                break;
            case R.id.btncreatenewpassword:
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                break;
            case R.id.btnforgotpassword:
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                break;
        }

    }
}

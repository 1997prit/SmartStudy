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

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etoldpassword, etnewpassword, etconfirmpassword;
    Button btnchangepassword;
    ProgressDialog progressDialog;
    SharedPreferences s1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initialize();
    }

    void initialize() {
        s1 = getSharedPreferences("MyFile", 0);
        etoldpassword = (EditText) findViewById(R.id.etstudentoldpassword);
        etnewpassword = (EditText) findViewById(R.id.etstudentnewpassword);
        etconfirmpassword = (EditText) findViewById(R.id.etstudentconfirmpassword);
        btnchangepassword = (Button) findViewById(R.id.btnchangepassword);
        btnchangepassword.setOnClickListener(this);
    }


    class changepassword extends AsyncTask<Void, Void, Void> {
        String result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ChangePasswordActivity.this);
            progressDialog.setMessage("Loading Please Wait");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            String json = jsonParser.invokeJSON("setdata", "query", "Update Student Set student_password = '" + etnewpassword.getText().toString() + "' where student_id =" + s1.getString(LoginActivity.TAG_STUDENT_ID, ""));
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
                SharedPreferences.Editor e = s1.edit();
                e.putString(LoginActivity.TAG_STUDENT_PASSWORD, etnewpassword.getText().toString());
                e.commit();
                Toast.makeText(ChangePasswordActivity.this, "Password Changed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ChangePasswordActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnchangepassword:
                if (validateForm())
                    new changepassword().execute();
                break;
        }
    }

    boolean validateForm() {
        boolean flag = true;
        if (etoldpassword.getText().toString().equals("")) {
            flag = false;
            etoldpassword.setError("Old Password is Required");
        } else if (!s1.getString(LoginActivity.TAG_STUDENT_PASSWORD, "").equals(etoldpassword.getText().toString())) {
            flag = false;
            etoldpassword.setError("Old Password does not match");
        }
        if (etnewpassword.getText().toString().equals("")) {
            flag = false;
            etnewpassword.setError("New Password is Required");
        }
        if (etconfirmpassword.getText().toString().equals("")) {
            flag = false;
            etconfirmpassword.setError("Confirm New Password is Required");
        } else if (!etconfirmpassword.getText().toString().equals(etnewpassword.getText().toString())) {
            flag = false;
            etconfirmpassword.setError("New Password and Confirm New Passwod donot match");
        }
        return flag;
    }
}
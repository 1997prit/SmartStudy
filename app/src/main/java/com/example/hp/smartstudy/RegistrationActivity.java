package com.example.hp.smartstudy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etstudentname, etstudentaddress, etstudentmobile, etstudentmail, etstudentpassword;
    Spinner etstudentclass, etstudentboard, etstudentschool;
    Button btnregistration;
    ProgressDialog progressDialog;
    public static final String TAG_CLASS = "class";
    public static final String TAG_CLASS_ID = "class_id";
    public static final String TAG_CLASS_NAME = "class_name";

    public static final String TAG_BOARD = "board";
    public static final String TAG_BOARD_ID = "board_id";
    public static final String TAG_BOARD_NAME = "board_name";

    public static final String TAG_SCHOOL = "school";
    public static final String TAG_SCHOOL_ID = "school_id";
    public static final String TAG_SCHOOL_NAME = "school_name";

    RadioGroup rgGender;
    RadioButton rb;

    public String schoolid[], schoolname[], boardid[], boardname[], classid[], classname[];
    public String selschoolid = "", selboradid = "", selclassid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initialize();
    }

    void initialize() {
        etstudentname = (EditText) findViewById(R.id.etrusername);
        etstudentaddress = (EditText) findViewById(R.id.etruseraddress);
        etstudentmobile = (EditText) findViewById(R.id.etrusermobile);
        etstudentmail = (EditText) findViewById(R.id.etrusermail);
        etstudentpassword = (EditText) findViewById(R.id.etruserpassword);
        rgGender = (RadioGroup) findViewById(R.id.rgGender);
        etstudentclass = (Spinner) findViewById(R.id.etruserclass);
        etstudentboard = (Spinner) findViewById(R.id.etruserboard);
        etstudentschool = (Spinner) findViewById(R.id.etruserschool);
        btnregistration = (Button) findViewById(R.id.btnregistration);
        btnregistration.setOnClickListener(this);
        new getBoard().execute();
    }

    class getBoard extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(RegistrationActivity.this);
            progressDialog.setMessage("Loading Please Wait for some time....");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String json = jsonParser.invokeJSON("getBoard");
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_BOARD);
                boardid = new String[jsonArray.length()];
                boardname = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    boardid[i] = jsonObject1.getString(TAG_BOARD_ID);
                    boardname[i] = jsonObject1.getString(TAG_BOARD_NAME);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            json = jsonParser.invokeJSON("getSchool");
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_SCHOOL);
                schoolid = new String[jsonArray.length()];
                schoolname = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    schoolid[i] = jsonObject1.getString(TAG_SCHOOL_ID);
                    schoolname[i] = jsonObject1.getString(TAG_SCHOOL_NAME);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            json = jsonParser.invokeJSON("getClass");
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_CLASS);
                classid = new String[jsonArray.length()];
                classname = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    classid[i] = jsonObject1.getString(TAG_CLASS_ID);
                    classname[i] = jsonObject1.getString(TAG_CLASS_NAME);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayAdapter<String> boardAdapter = new ArrayAdapter<String>(RegistrationActivity.this, android.R.layout.simple_spinner_dropdown_item, boardname);
            etstudentboard.setAdapter(boardAdapter);

            ArrayAdapter<String> classAdapter = new ArrayAdapter<String>(RegistrationActivity.this, android.R.layout.simple_spinner_dropdown_item, classname);
            etstudentclass.setAdapter(classAdapter);

            ArrayAdapter<String> schoolAdapter = new ArrayAdapter<String>(RegistrationActivity.this, android.R.layout.simple_spinner_dropdown_item, schoolname);
            etstudentschool.setAdapter(schoolAdapter);

            etstudentschool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selschoolid = schoolid[position];
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            etstudentclass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selclassid = classid[position];
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            etstudentboard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selboradid = boardid[position];
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            progressDialog.dismiss();

        }
    }

    class getRegistration extends AsyncTask<Void, Void, Void> {
        String result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(RegistrationActivity.this);
            progressDialog.setMessage("Please wait......");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {


            String json = jsonParser.invokeJSON("setdata", "query", "insert into Student(student_name,student_address,student_mobile,student_mail,student_password,student_gender,class_id,board_id,school_id)values('" + etstudentname.getText().toString() + "','" + etstudentaddress.getText().toString() + "'," + etstudentmobile.getText().toString() + ",'" + etstudentmail.getText().toString() + "','" + etstudentpassword.getText().toString() + "','" + rb.getText().toString() + "','" + selclassid + "','" + selboradid + "','" + selschoolid + "')");
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
                Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            } else {
                Toast.makeText(RegistrationActivity.this, "Error. Try Again", Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnregistration:
                rb = (RadioButton) findViewById(rgGender.getCheckedRadioButtonId());
                new getRegistration().execute();
                break;
        }

    }
}

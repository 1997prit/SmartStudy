package com.example.hp.smartstudy;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ChatActivity extends AppCompatActivity {
    ListView lvchat;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    public static final String TAG = "chat";
    public static final String TAG_CHAT_ID = "chat_id";
    public static final String TAG_CHAT_DATE = "chat_date";
    public static final String TAG_CHAT_DESCRIPTION = "chat_description";
    public static final String TAG_FACULTY_ID = "faculty_id";
    public static final String TAG_STUDENT_ID = "student_id";
    public static final String TAG_SENDER = "sender";
    ImageButton btnsend;
    EditText etMsg;
    ProgressDialog progressDialog;
    String query = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        lvchat = (ListView) findViewById(R.id.lvchat);
        btnsend = (ImageButton) findViewById(R.id.btnsendchat);
        etMsg = (EditText) findViewById(R.id.etMsg);
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date d1 = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yy");
                query = "Insert into Chat(chat_date,chat_description,faculty_id,student_id,sender)values('"
                        + dateFormat.format(d1)
                        + "','"
                        + etMsg.getText().toString()
                        + "','"
                        + getIntent().getStringExtra(FacultyActivity.TAG_FACULTY_ID)
                        + "','" + getSharedPreferences("MyFile", 0).getString(LoginActivity.TAG_STUDENT_ID, "") + "','student')";
                Log.d("query", query);
                new addchat().execute();
            }
        });
        new getChat().execute();
    }

    class getChat extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ChatActivity.this);
            progressDialog.setMessage("Loading Please Wait for some time....");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String json = jsonParser.invokeJSON("getChat", "values", getSharedPreferences("MyFile", 0).getString(LoginActivity.TAG_STUDENT_ID, "") + "," + getIntent().getStringExtra(FacultyActivity.TAG_FACULTY_ID));
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(TAG_CHAT_ID, jsonObject1.getString(TAG_CHAT_ID));
                    hashMap.put(TAG_CHAT_DATE, jsonObject1.getString(TAG_CHAT_DATE));
                    hashMap.put(TAG_CHAT_DESCRIPTION, jsonObject1.getString(TAG_CHAT_DESCRIPTION));
                    hashMap.put(TAG_FACULTY_ID, getIntent().getStringExtra(FacultyActivity.TAG_FACULTY_NAME));
                    hashMap.put(TAG_STUDENT_ID, getSharedPreferences("MyFile", 0).getString(LoginActivity.TAG_STUDENT_NAME, ""));
                    hashMap.put(TAG_SENDER, jsonObject1.getString(TAG_SENDER));
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
            ChatAdapter chatAdapter = new ChatAdapter(ChatActivity.this, arrayList);
            lvchat.setAdapter(chatAdapter);
            progressDialog.dismiss();
        }
    }

    class addchat extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ChatActivity.this);
            progressDialog.setMessage("Please wait");
            progressDialog.setTitle("Loading");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(Void... params) {
            String strjson = jsonParser.invokeJSON("setdata", "query", query);
            Log.d("res", strjson);
            try {
                JSONObject jsonObject = new JSONObject(strjson);
                return jsonObject.getString("Result");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid.equals("1")) {

                HashMap<String, String> hashMap = new HashMap<String, String>();
                Date d = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
                hashMap.put(TAG_CHAT_DATE, dateFormat.format(d));
                hashMap.put(TAG_FACULTY_ID, getIntent().getStringExtra(FacultyActivity.TAG_FACULTY_NAME));
                hashMap.put(TAG_CHAT_DESCRIPTION, etMsg.getText().toString());
                hashMap.put(TAG_STUDENT_ID, getSharedPreferences("MyFile", 0).getString(LoginActivity.TAG_STUDENT_NAME, ""));
                hashMap.put(TAG_SENDER, "student");
                arrayList.add(hashMap);
                ChatAdapter chatadapter = new ChatAdapter(ChatActivity.this,
                        arrayList);
                ListView listView = (ListView) findViewById(R.id.lvchat);
                listView.setAdapter(chatadapter);
                progressDialog.dismiss();
                etMsg.setText("");
            }
            progressDialog.dismiss();
        }
    }
}

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

public class ClassActivity extends AppCompatActivity {
    ListView lvclass;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    public static final String TAG = "class";
    public static final String TAG_CLASS_ID = "class_id";
    public static final String TAG_CLASS_NAME = "class_name";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        getSupportActionBar().setTitle("Select Class");
        lvclass = (ListView) findViewById(R.id.lvclass);
        new getClass().execute();
    }

    class getClass extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ClassActivity.this);
            progressDialog.setMessage("Loading Please Wait for some time....");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String json = jsonParser.invokeJSON("getClass");
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(TAG_CLASS_ID, jsonObject1.getString(TAG_CLASS_ID));
                    hashMap.put(TAG_CLASS_NAME, jsonObject1.getString(TAG_CLASS_NAME));
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
            ClassAdapter classAdapter = new ClassAdapter(ClassActivity.this, arrayList);
            lvclass.setAdapter(classAdapter);
            lvclass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(ClassActivity.this, SubjectActivity.class);
                    i.putExtra(TAG_CLASS_ID, arrayList.get(position).get(TAG_CLASS_ID));
                    startActivity(i);
                }
            });
            progressDialog.dismiss();
        }
    }
}

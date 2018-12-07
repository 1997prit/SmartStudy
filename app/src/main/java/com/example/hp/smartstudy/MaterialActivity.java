package com.example.hp.smartstudy;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MaterialActivity extends AppCompatActivity {
    ListView lvmaterial;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    public static final String TAG = "material";
    public static final String TAG_MATERIAL_ID = "material_id";
    public static final String TAG_MATERIAL_TITLE = "material_title";
    public static final String TAG_MATERIAL_DESCRIPTION = "material_description";
    public static final String TAG_MATERIAL_TITLE_URL = "material_title_url";
    public static final String TAG_CLASS_ID = "class_id";
    public static final String TAG_SUBJECT_ID = "subject_id";
    public static final String TAG_MATERIAL_TYPE_ID = "material_type_id";

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);
        lvmaterial = (ListView) findViewById(R.id.lvmaterial);
        new getMaterial().execute();
    }

    class getMaterial extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MaterialActivity.this);
            progressDialog.setMessage("Loading Please Wait for some time....");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            String json = jsonParser.invokeJSON("getMaterial", "params", getSharedPreferences("MyFile", 0).getString(SubjectActivity.TAG_SUBJECT_ID, "") + "," + getIntent().getStringExtra(MaterialtypeActivity.TAG_MATERIAL_TYPE_ID));
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(TAG_MATERIAL_ID, jsonObject1.getString(TAG_MATERIAL_ID));
                    hashMap.put(TAG_MATERIAL_TITLE, jsonObject1.getString(TAG_MATERIAL_TITLE));
                    hashMap.put(TAG_MATERIAL_DESCRIPTION, jsonObject1.getString(TAG_MATERIAL_DESCRIPTION));
                    hashMap.put(TAG_MATERIAL_TITLE_URL, jsonObject1.getString(TAG_MATERIAL_TITLE_URL));
                    hashMap.put(TAG_CLASS_ID, jsonObject1.getString(TAG_CLASS_ID));
                    hashMap.put(TAG_SUBJECT_ID, jsonObject1.getString(TAG_SUBJECT_ID));
                    hashMap.put(TAG_MATERIAL_TYPE_ID, jsonObject1.getString(TAG_MATERIAL_TYPE_ID));
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
            MaterialAdapter materialAdapter = new MaterialAdapter(MaterialActivity.this, arrayList);
            lvmaterial.setAdapter(materialAdapter);
            progressDialog.dismiss();
        }
    }
}

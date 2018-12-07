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

public class MaterialtypeActivity extends AppCompatActivity {
    ListView lvmaterialtype;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    public static final String TAG = "material_type";
    public static final String TAG_MATERIAL_TYPE_ID = "material_type_id";
    public static final String TAG_MATERIAL_TYPE_NAME = "material_type_name";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materialtype);
        getSupportActionBar().setTitle("Choose Material Type");
        lvmaterialtype = (ListView) findViewById(R.id.lvmaterialtype);
        new getMaterial_type().execute();
    }

    class getMaterial_type extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MaterialtypeActivity.this);
            progressDialog.setMessage("Loading Please Wait for some time....");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String json = jsonParser.invokeJSON("getMaterial_type");
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(TAG_MATERIAL_TYPE_ID, jsonObject1.getString(TAG_MATERIAL_TYPE_ID));
                    hashMap.put(TAG_MATERIAL_TYPE_NAME, jsonObject1.getString(TAG_MATERIAL_TYPE_NAME));
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
            MaterialtypeAdapter materialtypeAdapter = new MaterialtypeAdapter(MaterialtypeActivity.this, arrayList);
            lvmaterialtype.setAdapter(materialtypeAdapter);
            lvmaterialtype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(MaterialtypeActivity.this, MaterialActivity.class);
                    i.putExtra(TAG_MATERIAL_TYPE_ID, arrayList.get(position).get(TAG_MATERIAL_TYPE_ID));
                    startActivity(i);
                }
            });
            progressDialog.dismiss();

        }
    }
}

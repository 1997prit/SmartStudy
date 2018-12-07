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

public class AreaActivity extends AppCompatActivity {
    ListView lvarea;
    ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();
    public static final String TAG="area";
    public static final String TAG_AREA_ID="area_id";
    public static final String TAG_AREA_NAME="area_name";
    public static final String TAG_CITY_ID="city_id";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);
        lvarea=(ListView)findViewById(R.id.lvarea);
        new getArea().execute();
    }
    class getArea extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(AreaActivity.this);
            progressDialog.setMessage("Loading Please Wait for some time....");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String json=jsonParser.invokeJSON("getArea");
            try {
                JSONObject jsonObject=new JSONObject(json);
                JSONArray jsonArray=jsonObject.getJSONArray(TAG);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                    HashMap<String,String>hashMap=new HashMap<>();
                    hashMap.put(TAG_AREA_ID,jsonObject1.getString(TAG_AREA_ID));
                    hashMap.put(TAG_AREA_NAME,jsonObject1.getString(TAG_AREA_NAME));
                    hashMap.put(TAG_CITY_ID,jsonObject1.getString(TAG_CITY_ID));
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
            AreaAdapter areaAdapter=new AreaAdapter(AreaActivity.this,arrayList);
            lvarea.setAdapter(areaAdapter);
            progressDialog.dismiss();

        }
    }
}

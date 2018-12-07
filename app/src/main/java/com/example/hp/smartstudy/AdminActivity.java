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

public class AdminActivity extends AppCompatActivity {
    ListView lvadmin;
    ArrayList<HashMap<String,String>>arrayList=new ArrayList<>();
    public static final String TAG="admin";
    public static final String TAG_ADMIN_ID="admin_id";
    public static final String TAG_ADMIN_MAIL="admin_mail";
    public static final String TAG_ADMIN_PASSWORD="admin_password";
ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        lvadmin=(ListView)findViewById(R.id.lvadmin);
        new getAdmin().execute();
    }
  class getAdmin extends AsyncTask<Void ,Void,Void>{
      @Override
      protected void onPreExecute() {
          super.onPreExecute();
          progressDialog=new ProgressDialog(AdminActivity.this);
          progressDialog.setMessage("Loading Please Wait for some time....");
          progressDialog.show();
      }

      @Override
      protected Void doInBackground(Void... params) {
          String json= jsonParser.invokeJSON("getAdmin");
          try {
              JSONObject jsonObject=new JSONObject(json);
              JSONArray jsonArray=jsonObject.getJSONArray(TAG);
              for(int i=0;i<jsonArray.length();i++){
                  JSONObject jsonObject1=jsonArray.getJSONObject(i);
                  HashMap<String,String>hashMap=new HashMap<>();
                  hashMap.put(TAG_ADMIN_ID,jsonObject1.getString(TAG_ADMIN_ID));
                  hashMap.put(TAG_ADMIN_MAIL,jsonObject1.getString(TAG_ADMIN_MAIL));
                  hashMap.put(TAG_ADMIN_PASSWORD,jsonObject1.getString(TAG_ADMIN_PASSWORD));
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
          AdminAdapter adminAdapter=new AdminAdapter(AdminActivity.this,arrayList);
          lvadmin.setAdapter(adminAdapter);
          progressDialog.dismiss();


      }
  }
}

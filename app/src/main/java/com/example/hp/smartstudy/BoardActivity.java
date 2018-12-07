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
import java.util.List;

public class BoardActivity extends AppCompatActivity {
    ListView lvboard;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    public static final String TAG = "board";
    public static final String TAG_BOARD_ID = "board_id";
    public static final String TAG_BOARD_NAME = "board_name";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        lvboard = (ListView) findViewById(R.id.lvboard);
        new getBoard().execute();

    }

    class getBoard extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(BoardActivity.this);
            progressDialog.setMessage("Loading Please Wait for some time....");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String json = jsonParser.invokeJSON("getBoard");
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(TAG_BOARD_ID, jsonObject1.getString(TAG_BOARD_ID));
                    hashMap.put(TAG_BOARD_NAME, jsonObject1.getString(TAG_BOARD_NAME));
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
            BoardAdapter boardAdapter = new BoardAdapter(BoardActivity.this, arrayList);
            lvboard.setAdapter(boardAdapter);
            lvboard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startActivity(new Intent(BoardActivity.this, ClassActivity.class));
                }
            });
            progressDialog.dismiss();
        }
    }
}

package com.example.hp.smartstudy;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class DictionaryActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    ListView lvdictionary;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    public static final String TAG = "dictionary";
    public static final String TAG_DICTIONARY_WORD_ID = "d_word_id";
    public static final String TAG_DICTIONARYWORD_NAME = "d_wordname";
    public static final String TAG_DICTIONARYWORD_DESCRIPTION = "d_w_description";
    ProgressDialog progressDialog;
    TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        lvdictionary = (ListView) findViewById(R.id.lvdictionary);
        t1 = new TextToSpeech(this, this);

        new getDictionary().execute();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = t1.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speakOut("");
            }
        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }


    class getDictionary extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(DictionaryActivity.this);
            progressDialog.setMessage("Loading Please Wait for some time....");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String json = jsonParser.invokeJSON("getDictionary");
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(TAG_DICTIONARY_WORD_ID, jsonObject1.getString(TAG_DICTIONARY_WORD_ID));
                    hashMap.put(TAG_DICTIONARYWORD_NAME, jsonObject1.getString(TAG_DICTIONARYWORD_NAME));
                    hashMap.put(TAG_DICTIONARYWORD_DESCRIPTION, jsonObject1.getString(TAG_DICTIONARYWORD_DESCRIPTION));
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

            DictionaryAdapter dictionaryAdapter = new DictionaryAdapter(DictionaryActivity.this, arrayList);
            lvdictionary.setAdapter(dictionaryAdapter);
            lvdictionary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    Toast.makeText(DictionaryActivity.this,"Meaning:" + arrayList.get(position).get(TAG_DICTIONARYWORD_DESCRIPTION), Toast.LENGTH_LONG).show();
                    speakOut(arrayList.get(position).get(TAG_DICTIONARYWORD_NAME));
                }
            });
            progressDialog.dismiss();
        }
    }

    private void speakOut(String text) {
        t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void onPause() {
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }
}
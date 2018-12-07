package com.example.hp.smartstudy;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread t1 = new Thread() {
            public void run() {
                try {
                    Thread.sleep(5000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (getSharedPreferences("MyFile", 0).getString(LoginActivity.TAG_STUDENT_ID, "").equals("")) {
                        Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(i);
                    } else {
                        Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(i);
                    }

                }
            }

        };
        t1.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

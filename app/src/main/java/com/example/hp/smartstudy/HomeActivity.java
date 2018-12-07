package com.example.hp.smartstudy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPreferences = getSharedPreferences("MyFile", 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView tvUname = (TextView) header.findViewById(R.id.tvUname);
        tvUname.setText("WelCome , " + sharedPreferences.getString(LoginActivity.TAG_STUDENT_NAME, ""));
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(HomeActivity.this, HomeActivity.class));
            finish();
        } else if (id == R.id.nav_material) {
            startActivity(new Intent(HomeActivity.this, ClassActivity.class));
        } else if (id == R.id.nav_quiz) {
            startActivity(new Intent(HomeActivity.this, QuizActivity.class));
        } else if (id == R.id.nav_quiz_result) {
            startActivity(new Intent(HomeActivity.this, StudentquizActivity.class));
        } else if (id == R.id.nav_chat) {
            startActivity(new Intent(HomeActivity.this, FacultyActivity.class));
        } else if (id == R.id.nav_feedback) {
            startActivity(new Intent(HomeActivity.this, FeedbackActivity.class));
        } else if (id == R.id.nav_contactus) {
            startActivity(new Intent(HomeActivity.this, ContactusActivity.class));
        } else if (id == R.id.nav_aboutus) {
            startActivity(new Intent(HomeActivity.this, AboutusActivity.class));
        } else if (id == R.id.nav_logout) {
            SharedPreferences.Editor e = sharedPreferences.edit();
            e.clear();
            e.commit();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else if (id == R.id.nav_dictionary) {
            startActivity(new Intent(HomeActivity.this, DictionaryActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

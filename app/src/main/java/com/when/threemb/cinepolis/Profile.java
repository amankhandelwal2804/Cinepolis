package com.when.threemb.cinepolis;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class Profile extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigation= (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        navigation.postDelayed(new Runnable() {
            @Override
            public void run() {

                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {

                } else if (itemId == R.id.navigation_dashboard) {
                    startActivity(new Intent(Profile.this, Dashboard.class));
                } else if (itemId == R.id.navigation_notifications) {
                    startActivity(new Intent(Profile.this, Transaction.class));
                }
                finish();
            }
        }, 300);

        return true;
    }
}

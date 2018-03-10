package com.when.threemb.cinepolis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    public static String REGISTER_URL="http://5b2cadbc.ngrok.io";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);//LOGIN
                    setTitle("Login");
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, fragment);
                    //fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);//REGISTER
                    setTitle("Register");
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, fragment2);
                    //fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_notifications);//ADMIN
                    Intent intent=new Intent(MainActivity.this,Dashboard.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }

    };
    FragmentTransaction fragmentTransaction;
    LoginFragment fragment;
    RegisterFragment fragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp=getSharedPreferences("LoginInfo",MODE_PRIVATE);
        int status=sp.getInt("status",0);
        if(status==1) {
            Intent intent = new Intent(MainActivity.this, Dashboard.class);
            startActivity(intent);
            finish();
        }
        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

         //fragmentManager = getFragmentManager();
         fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragment = new LoginFragment();
        fragment2 = new RegisterFragment();
        fragmentTransaction.add(R.id.content, fragment);
        fragmentTransaction.commit();

    }

    public void loginClick(View v)
    {
        //Toast.makeText(MainActivity.this, "Yo Clicked", Toast.LENGTH_SHORT).show();
        if(!laaDo())
        Snackbar.make(v,"No Internet Connection", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        else
        {
            fragment.loginUser();
        }
    }

    public void registerClick(View v)
    {
        //Toast.makeText(MainActivity.this, "Yo Clicked", Toast.LENGTH_SHORT).show();
        if(!laaDo())
            Snackbar.make(v,"No Internet Connection", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        else
        {
            fragment2.registerUser();
        }
    }

    public Boolean laaDo(/*View view*/)//Check internet connection
    {

        //Toast.makeText(MainActivity.this, "Please Wait !!", Toast.LENGTH_SHORT).show();
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
            return true;

        } else {
            // display error
            Toast.makeText(MainActivity.this, "NO INTERNET CONNECTION !!", Toast.LENGTH_SHORT).show();

            return false;

        }

    }

}

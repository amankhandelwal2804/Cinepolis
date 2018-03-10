package com.when.threemb.cinepolis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Transaction extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    protected BottomNavigationView navigation;
    List<String> list;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        navigation= (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        ListView lv = (ListView)findViewById(R.id.listview);
        list = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(arrayAdapter);
        showTickets();
        // we can add item as list.add("  ");

        //arrayAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        navigation.postDelayed(new Runnable() {
            @Override
            public void run() {

                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                     startActivity(new Intent(Transaction.this, Profile.class));
                } else if (itemId == R.id.navigation_dashboard) {
                      startActivity(new Intent(Transaction.this, Dashboard.class));
                } else if (itemId == R.id.navigation_notifications) {

                }
                finish();
            }
        }, 300);

        return true;
    }

    public void showTickets() {
        SharedPreferences sp=getSharedPreferences("LoginInfo",MODE_PRIVATE);
        final String username=sp.getString("username","root");
        arrayAdapter.clear();

        if (username.length() != 0) {
            RequestQueue requestQueue = Volley.newRequestQueue(Transaction.this);
            //TODO change get url pass group and sem
            String urli = MainActivity.REGISTER_URL+"/tickets?"+"username"+"="+username;
            Toast.makeText(this, "url: "+urli, Toast.LENGTH_SHORT).show();

            //Toast.makeText(this, "VOLLEY CALLED", Toast.LENGTH_SHORT).show();


            final JsonArrayRequest kor = new JsonArrayRequest(urli,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray ob) {

                            if (ob != null) {
                                String details="";
                                for(int i=0;i<ob.length();i++) {
                                    JSONObject object=ob.optJSONObject(i);
                                    details+="ticket ID: "+object.optInt("ticket_id")+" ";
                                    details+="Seat No."+object.optInt("seat_no")+" ";
                                    details+="Date: "+object.optString("show_date")+" ";
                                    details+="Movie ID:"+object.optInt("movie_id")+" ";
                                    details+="time: "+object.optInt("ticket_id")+" ";
                                    details+="Screen No."+object.optInt("screen_no")+" ";
                                    list.add(details);
                                }
                                arrayAdapter.notifyDataSetChanged();

                            }
                            else
                            {
                                Toast.makeText(Transaction.this, "NO MOVIES IN YOUR CITY", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Volley", "Error");
                        }
                    }
            );

            requestQueue.add(kor);
        }
        else
        {
            Toast.makeText(Transaction.this,"Field Cannot be left blank",Toast.LENGTH_SHORT).show();

        }
    }

}

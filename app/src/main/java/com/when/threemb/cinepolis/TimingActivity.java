package com.when.threemb.cinepolis;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimingActivity extends AppCompatActivity{

    RecyclerView rv;
    int a[]={1130,1245,1310,420};
    int b[]={1045,1122,1454};
    TimingAdapter adapter;
    private ArrayList<TimingTemplate> timingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timing);
        rv=(RecyclerView)findViewById(R.id.timing_view);
        timingList = new ArrayList<>();
        rv.setLayoutManager(new LinearLayoutManager(this));
        //Toast.makeText(this,timingList.toString(), Toast.LENGTH_SHORT).show();
        adapter=new TimingAdapter(this,timingList);
        //DerpData Creates and returns a List<TimingTemplates>
        rv.setAdapter(adapter);
        prepareTimingList();

        //adapter.setItemClickCallback(TimingActivity.this);
    }

    public void prepareTimingList() {

        SharedPreferences sp=getSharedPreferences("LoginInfo",MODE_PRIVATE);

        final String city = sp.getString("city","mumbai");
        String REGISTER_URL=MainActivity.REGISTER_URL;

        Bundle kuchbhi=getIntent().getExtras();
        final int movie_id=kuchbhi.getInt("movie_id",0);

        if (city.length()>0) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            //TODO change get url pass group and sem
            String urli = REGISTER_URL+"/shows?"+"city"+"="+city+"&movie_id="+movie_id+"&date=2017-11-09";
            Toast.makeText(this, "url: "+urli, Toast.LENGTH_SHORT).show();

            //Toast.makeText(this, "VOLLEY CALLED", Toast.LENGTH_SHORT).show();


            final JsonArrayRequest kor = new JsonArrayRequest(urli,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray ob) {

                            if (ob != null) {
                                int screen_no,  theatre_id,  ticket_amt, time[];
                                String theatre_name;
                                for(int i=0;i<ob.length();i++) {
                                    JSONObject object=ob.optJSONObject(i);
                                    theatre_name=object.optString("theatre_name");
                                    screen_no=object.optInt("screen_no");
                                    theatre_id=object.optInt("theatre_id");
                                    ticket_amt=object.optInt("ticket_amt");
                                    JSONArray temp_array=object.optJSONArray("time");
                                    time=new int[temp_array.length()];
                                        for(int j=0;j<temp_array.length();j++)
                                            time[j]=temp_array.optInt(j);
                                    TimingTemplate tt=new TimingTemplate(ticket_amt,time,theatre_name,"",theatre_id,screen_no,"2017-11-09",movie_id);
                                    timingList.add(tt);
                                }
                                adapter.notifyDataSetChanged();

                            }
                            else
                            {
                                Toast.makeText(TimingActivity.this, "NO MOVIES IN YOUR CITY", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(TimingActivity.this,"Field Cannot be left blank",Toast.LENGTH_SHORT).show();

        }
    }



}


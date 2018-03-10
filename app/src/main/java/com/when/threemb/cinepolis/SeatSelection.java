package com.when.threemb.cinepolis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class SeatSelection extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    LinearLayout containLayout;
    CheckBox seat;
    int seatCount=0;
    int movie_id,theatre_id,screen_no,time,ticket_amt,seats[];
    String date;
    int leng=-1;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        int i, j;
        containLayout = (LinearLayout) findViewById(R.id.container);

        for (i = 0; i < 10; i++) {
            LinearLayout row = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            if(i==2 || i==7)
            {
                params.setMargins(0,50,0,0);
            }
            row.setLayoutParams(params);
            row.setId(i+200);
            row.setOrientation(LinearLayout.HORIZONTAL);

            for (j = 0; j < 10; j++) {
                CheckBox seat = new CheckBox(this);
                seat.setText("");
                if(j==2 || j==7)
                {
                    seat.setText("...");
                }
                int seat_id=i * 10 + j;
                seat.setId(seat_id);
                seat.setButtonDrawable(R.drawable.seat);
                seat.setOnCheckedChangeListener(this);
                seat.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

                row.addView(seat);
            }
            containLayout.addView(row);
        }

        prepareSeatList();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(this, "Clicked:"+buttonView.getId(), Toast.LENGTH_SHORT).show();
        if(isChecked==true)
            seatCount++;
        else
            seatCount--;
        String seatText;
        if(seatCount==0)
            seatText="Please select your seats";
        else
            seatText=seatCount+" seats selected";
        final TextView sCount=(TextView)findViewById(R.id.seatCount);
        sCount.setText(seatText);
    }



    public void prepareSeatList() {

        String REGISTER_URL=MainActivity.REGISTER_URL;

        Bundle kuchbhi=getIntent().getExtras();
        //movie_id=1&theatre_id=5&screen_no=6&time=1145&date=2017-11-09
          movie_id=kuchbhi.getInt("movie_id",0);
          theatre_id=kuchbhi.getInt("theatre_id",0);
          screen_no=kuchbhi.getInt("screen_no",0);
          time=kuchbhi.getInt("time",0);
          ticket_amt=kuchbhi.getInt("ticket_amt",0);
          date=kuchbhi.getString("date","2017-11-09");

        if (true) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            //TODO change get url pass group and sem
            String urli = REGISTER_URL+"/seats?"+"movie_id"+"="+movie_id+"&theatre_id="+theatre_id+"&screen_no="+screen_no+"&time="+time+"&date="+date;
            Log.d("My URL:",urli);
            Toast.makeText(this, "url: "+urli, Toast.LENGTH_SHORT).show();

            //Toast.makeText(this, "VOLLEY CALLED", Toast.LENGTH_SHORT).show();


            final JsonArrayRequest kor = new JsonArrayRequest(urli,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray ob) {

                            if (ob != null) {
                                    leng=ob.length();
                                    Log.e("JSON Length",leng+"");
                                    seats=new int[ob.length()];
                                    for(int x=0;x<ob.length();x++)
                                    {
                                        seats[x]=ob.optInt(x);
                                        CheckBox c=(CheckBox)findViewById(seats[x]);
                                        c.setEnabled(false);
                                    }

                            }
                            else
                            {
                                seats=new int[1];
                                seats[0]=-1;
                                Toast.makeText(SeatSelection.this, "ALL SEATS ARE EMPTY", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(SeatSelection.this,"Some error occurred",Toast.LENGTH_SHORT).show();

        }
    }

    public void bookTickets(View v){
        for(int i=0;i<100;i++)
        {
            CheckBox c=(CheckBox)findViewById(i);
            if(c.isChecked())
            {
                bookSeat(i);
            }
        }
    }

    public void bookSeat(int seatno) {

        String REGISTER_URL=MainActivity.REGISTER_URL;

        Bundle kuchbhi=getIntent().getExtras();
        //movie_id=1&theatre_id=5&screen_no=6&time=1145&date=2017-11-09
        movie_id=kuchbhi.getInt("movie_id",0);
        theatre_id=kuchbhi.getInt("theatre_id",0);
        screen_no=kuchbhi.getInt("screen_no",0);
        time=kuchbhi.getInt("time",0);
        ticket_amt=kuchbhi.getInt("ticket_amt",0);
        date=kuchbhi.getString("date","2017-11-09");

        SharedPreferences sp=getSharedPreferences("LoginInfo",MODE_PRIVATE);
        String username=sp.getString("username","root");

        if (true) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            //TODO change get url pass group and sem
            String urli = REGISTER_URL+"/book?"+"movie_id"+"="+movie_id+"&theatre_id="+theatre_id+"&screen_no="+screen_no+"&time="+time+"&date="+date+
                    "&username="+username+"&ticket_amt="+ticket_amt+"&seat_no="+seatno;
            Log.d("My URL:",urli);
            Toast.makeText(this, "url: "+urli, Toast.LENGTH_SHORT).show();

            //Toast.makeText(this, "VOLLEY CALLED", Toast.LENGTH_SHORT).show();


            final JsonObjectRequest kor = new JsonObjectRequest(Request.Method.GET, urli, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject ob) {

                            if (ob != null) {
                                String response=ob.optString("status");
                                Toast.makeText(SeatSelection.this, "json: "+response, Toast.LENGTH_SHORT).show();
                                if(response.equalsIgnoreCase("Failed"))
                                {
                                    Toast.makeText(SeatSelection.this,"Incorrect Credentials", Toast.LENGTH_LONG).show();
                                    //FirebaseAuth.getInstance().signOut();
                                }
                                else
                                {
                                    Toast.makeText(SeatSelection.this, "Booked Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Volley", "Error");
                            Toast.makeText(SeatSelection.this, "No data recieved", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

            requestQueue.add(kor);
        }
        else
        {
            Toast.makeText(SeatSelection.this,"Some error occurred",Toast.LENGTH_SHORT).show();

        }
    }


}

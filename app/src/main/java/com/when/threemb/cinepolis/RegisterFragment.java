package com.when.threemb.cinepolis;

/**
 * Created by User on 11/5/2017.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {

    EditText etUsername,etPassword,etCity,etMobile,etEmail;
    String REGISTER_URL=MainActivity.REGISTER_URL,
            KEY_USERNAME="username",KEY_PASSWORD="password",
            KEY_CITY="city",KEY_MOBILE="mobile_no",KEY_EMAIL="email";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootview= inflater.inflate(R.layout.register, container, false);

        etPassword=(EditText)rootview.findViewById(R.id.password);
        etUsername=(EditText)rootview.findViewById(R.id.username);
        etCity=(EditText)rootview.findViewById(R.id.city);
        etMobile=(EditText)rootview.findViewById(R.id.mobile);
        etEmail=(EditText)rootview.findViewById(R.id.email);



        return rootview;
    }

    public void registerUser() {
        final String username = etUsername.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();
        final String city = etCity.getText().toString().trim();
        final String mobile = etMobile.getText().toString().trim();
        final String email = etEmail.getText().toString().trim();

        if (username.length()>0 && password.length()>0 && city.length()>0 && email.length()>0 && mobile.length()>0) {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            //TODO change get url pass group and sem
            String urli = REGISTER_URL+"/register?"+KEY_USERNAME+"="+username+"&"+KEY_PASSWORD+"="+password+"&"+KEY_CITY+"="+ city+"&"+KEY_MOBILE+"="+ mobile+"&"+KEY_EMAIL+"="+ email;
            Toast.makeText(getActivity(), "url: "+urli, Toast.LENGTH_SHORT).show();

            //Toast.makeText(this, "VOLLEY CALLED", Toast.LENGTH_SHORT).show();


            final JsonObjectRequest kor = new JsonObjectRequest(Request.Method.GET, urli, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject ob) {

                            if (ob != null) {
                                String response=ob.optString("status");
                                Toast.makeText(getActivity(), "json: "+response, Toast.LENGTH_SHORT).show();
                                if(response.equalsIgnoreCase("Failed"))
                                {
                                    Toast.makeText(getActivity(),"Incorrect Credentials", Toast.LENGTH_LONG).show();
                                    //FirebaseAuth.getInstance().signOut();
                                }
                                else
                                {
                                    SharedPreferences sp;
                                    SharedPreferences.Editor editor;
                                    sp=getActivity().getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
                                    editor=sp.edit();
                                    editor.putInt("status",1);
                                    editor.putString("username",username);
                                    editor.putString("city",city);
                                    editor.commit();
                                    Intent intent=new Intent(getActivity(),SeatSelection.class);
                                    startActivity(intent);
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Volley", "Error");
                            Toast.makeText(getContext(), "Error"+error, Toast.LENGTH_SHORT).show();
                        }
                    }
            );

            requestQueue.add(kor);
        }
        else
        {
            Toast.makeText(getActivity(),"Field Cannot be left blank",Toast.LENGTH_SHORT).show();

        }
    }




}
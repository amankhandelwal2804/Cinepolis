package com.when.threemb.cinepolis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 11/5/2017.
 */

public class LoginFragment extends Fragment {

   EditText etUsername,etPassword,etCity,etMobile,etEmail;
   String REGISTER_URL=MainActivity.REGISTER_URL,
           KEY_USERNAME="username",KEY_PASSWORD="password";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.login, container, false);

        etPassword=(EditText)rootview.findViewById(R.id.password);
        etUsername=(EditText)rootview.findViewById(R.id.username);

        return rootview;
    }


    public void loginUser() {
        final String username = etUsername.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();

        if (username.length() != 0 && password.length() != 0) {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            //TODO change get url pass group and sem
            String urli = REGISTER_URL+"/login?"+KEY_USERNAME+"="+username+"&"+KEY_PASSWORD+"="+password;
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
                                    Toast.makeText(getActivity(), "City:" + ob.optString("city"), Toast.LENGTH_SHORT).show();
                                    SharedPreferences sp;
                                    SharedPreferences.Editor editor;
                                    sp=getActivity().getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
                                    editor=sp.edit();
                                    editor.putInt("status",1);
                                    editor.putString("username",username);
                                    editor.putString("city",ob.optString("city"));
                                    editor.commit();
                                    Intent intent=new Intent(getActivity(),Dashboard.class);
                                    startActivity(intent);
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Volley", "Error");
                            Toast.makeText(getContext(), "No data recieved", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

            requestQueue.add(kor);
        }
        else
        {
            Toast.makeText(getActivity(),"Invalid Username/Password",Toast.LENGTH_SHORT).show();

        }
    }

}
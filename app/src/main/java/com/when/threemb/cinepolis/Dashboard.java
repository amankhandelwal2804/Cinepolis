package com.when.threemb.cinepolis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    protected BottomNavigationView navigation;
    private  AlbumAdadpter adadpter;
    private List<Album> albumList;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigation= (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adadpter = new AlbumAdadpter(this, albumList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(mLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),0);
        recyclerView.addItemDecoration(dividerItemDecoration);

        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adadpter);

        prepareAlbums();

        try {
            Glide.with(this).load(R.drawable.theater).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void prepareAlbums() {

        SharedPreferences sp=getSharedPreferences("LoginInfo",MODE_PRIVATE);

        final String city = sp.getString("city","mumbai");
        String REGISTER_URL=MainActivity.REGISTER_URL;

        if (city.length()>0) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            //TODO change get url pass group and sem
            String urli = REGISTER_URL+"/movies?"+"city"+"="+city;
            Toast.makeText(this, "url: "+urli, Toast.LENGTH_SHORT).show();

            //Toast.makeText(this, "VOLLEY CALLED", Toast.LENGTH_SHORT).show();


            final JsonArrayRequest kor = new JsonArrayRequest(urli,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray ob) {

                            if (ob != null) {
                                String movie_name,  img_url,  description,  release_date;
                                int movie_id;
                                for(int i=0;i<ob.length();i++) {
                                    JSONObject object=ob.optJSONObject(i);
                                    movie_name=object.optString("movie_name");
                                    img_url=object.optString("img_url");
                                    description=object.optString("description");
                                    release_date=object.optString("release_date");
                                    movie_id=object.optInt("movie_id");
                                    Album album = new Album(movie_name, img_url, description, release_date, movie_id);
                                    albumList.add(album);
                                }
                                adadpter.notifyDataSetChanged();

                            }
                            else
                            {
                                Toast.makeText(Dashboard.this, "NO MOVIES IN YOUR CITY", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(Dashboard.this,"Field Cannot be left blank",Toast.LENGTH_SHORT).show();

        }
    }


    /*private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };*/
    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    toolbar.setBackgroundColor(ContextCompat.getColor(Dashboard.this, R.color.colorPrimaryDark));
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    toolbar.setBackgroundColor(ContextCompat.getColor(Dashboard.this, R.color.alpha));
                    isShow = false;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }
    private void updateNavigationBarState(){
        int actionId = R.id.navigation_dashboard;
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        Menu menu = navigation.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            boolean shouldBeChecked = item.getItemId() == itemId;
            if (shouldBeChecked) {
                item.setChecked(true);
                break;
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        navigation.postDelayed(new Runnable() {
            @Override
            public void run() {

                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    startActivity(new Intent(Dashboard.this, Profile.class));
                } else if (itemId == R.id.navigation_dashboard) {
                    //  startActivity(new Intent(this, DashboardActivity.class));
                } else if (itemId == R.id.navigation_notifications) {
                    startActivity(new Intent(Dashboard.this, Transaction.class));
                }
                finish();
            }
            }, 300);

        return true;
    }
}

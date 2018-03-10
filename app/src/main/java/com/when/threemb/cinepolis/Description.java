package com.when.threemb.cinepolis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Description extends AppCompatActivity {
    TextView title, realease, tvdescription,book;
    ImageView cover;
    Bundle kuchbhi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        title = (TextView)findViewById(R.id.tv_title);
        realease =(TextView) findViewById(R.id.tv_realease);
        tvdescription = (TextView)findViewById(R.id.tv_description);
        cover = (ImageView) findViewById(R.id.im_cover);
        book =(TextView) findViewById(R.id.tv_book);

        kuchbhi=getIntent().getExtras();
        if(kuchbhi==null) {
            Toast.makeText(this, "No data recieved from Dashboard", Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            String movie_name,  img_url,  description,  release_date;
            int movie_id;
            movie_id=kuchbhi.getInt("movie_id",-1);
            img_url=kuchbhi.getString("img_url","https://ih1.redbubble.net/image.226096856.2082/sticker,375x360-bg,ffffff.u1.png");
            description=kuchbhi.getString("description","Movie Not Found");
            release_date=kuchbhi.getString("release_date","");
            movie_name=kuchbhi.getString("movie_name","Error Occurred");

            title.setText(movie_name);
            realease.setText(release_date);
            tvdescription.setText(description);
            Glide.with(Description.this).load(img_url).asBitmap().into(cover);

        }

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Description.this,TimingActivity.class);
                intent.putExtras(kuchbhi);
                startActivity(intent);
            }
        });

        /*
            {
                title: 'thor',
                release: '30/5/2017'
                description: ' Aman says its a great movie'

            }


         */


    }
}

package com.when.threemb.cinepolis;

/*
 * Created by Rohit on 06-11-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AlbumAdadpter extends RecyclerView.Adapter<AlbumAdadpter.MyViewHolder>{
    private Context mContext;
    private List<Album> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public ImageView thumbnail;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        }
    }

    public AlbumAdadpter(Context mContext, List<Album> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Album album = albumList.get(position);
        holder.title.setText(album.getMovie_name());
        Glide.with(mContext).load(album.getImg_url()).asBitmap().into(holder.thumbnail);
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movie_name,  img_url,  description,  release_date;
                int movie_id;
                movie_name=album.getMovie_name();
                img_url=album.getImg_url();
                description=album.getDescription();
                release_date=album.getRelease_date();
                movie_id=album.getMovie_id();
                Intent i = new Intent(mContext,Description.class);
                i.putExtra("movie_id",movie_id);
                i.putExtra("img_url",img_url);
                i.putExtra("description",description);
                i.putExtra("release_date",release_date);
                i.putExtra("movie_name",movie_name);

                //TODO: add the put extra and call for a get request
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return albumList.size() ;
    }


}

package com.when.threemb.cinepolis;

/**
 * Created by User on 11/7/2017.
 */

        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.List;


/**
 * Created by User on 4/23/2017.
 */

public class TimingAdapter extends RecyclerView.Adapter<TimingAdapter.TimingHolder>{

    List<TimingTemplate> listData;
    LayoutInflater inflater;
    //private ItemClickCallback itemClickCallback;//created an object of the interface
    Context c,innerC;

    /*public interface ItemClickCallback {
        void onItemClick(int p);
        void onSecondaryIconClick(int p,int time);
    }*/

    /*public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;//Got the context of the object clicked  and placed it in the local object
    }*/

    public TimingAdapter(Context c, List<TimingTemplate> listData) {
        this.inflater = LayoutInflater.from(c);
        this.listData = listData;
        this.c=c;
    }

    @Override
    public TimingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.timing_item,parent,false);
        return new TimingHolder(view);
    }

    TimingTemplate item;
    @Override
    public void onBindViewHolder(TimingHolder holder, int position) {
        item = listData.get(position);
        holder.title.setText(item.getTheatre_name());
        holder.subtitle.setText(/*item.getTheatre_address()+*/" Screen No. "+item.getScreen_no());
        int timing[]=item.getTiming();
        final TimingTemplate item1=item;
        for(int i=0;i<timing.length;i++)
        {
            TextView tv=new TextView(c);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(25,5,25,5);
            tv.setLayoutParams(params);
            tv.setText(""+timing[i]);
            tv.setTextAppearance(c,R.style.textstyle);

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView tv=(TextView)v;
                    Toast.makeText(c, item1.getTheatre_name()+" "+tv.getText().toString(), Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(c,SeatSelection.class);
                    intent.putExtra("movie_id",item1.getMovie_id());
                    intent.putExtra("screen_no",item1.getScreen_no());
                    intent.putExtra("theatre_id",item1.getTheatre_id());
                    intent.putExtra("date",item1.getDate());
                    intent.putExtra("time",Integer.parseInt(tv.getText().toString()));
                    intent.putExtra("ticket_amt",item1.getTicket_amt());
                    c.startActivity(intent);
                }
            });
            holder.timing_container.addView(tv);
        }
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }

    class TimingHolder extends RecyclerView.ViewHolder {

        TextView title,subtitle;
        LinearLayout timing_container;
        View container;


        public TimingHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.theatre_name);
            subtitle=(TextView)itemView.findViewById(R.id.theatre_address);
            timing_container=(LinearLayout)itemView.findViewById(R.id.timing_container);



            container=(View)itemView.findViewById(R.id.cont_item_root);
            //secondaryIcon.setOnClickListener(this);
            //container.setOnClickListener(this);
        }


    }
}


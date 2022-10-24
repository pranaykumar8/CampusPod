package com.miniproject.myapp.student;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.miniproject.myapp.R;


public class EventViewPageAdapter extends FirebaseRecyclerAdapter<EventPageViewData, EventViewPageAdapter.MyViewholder> {

    /**
     * Initialize a {@link Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public EventViewPageAdapter(@NonNull FirebaseRecyclerOptions<EventPageViewData> options) {
        super(options);
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eventviewitem,parent,false);
        return new MyViewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewholder holder, int position, @NonNull EventPageViewData model) {

        holder.Stitle.setText(model.getEventtitle());
        holder.Sdate.setText(model.getEventdate());
        holder.Stime.setText(model.getEventtime());
        holder.Svenue.setText(model.getEventvenue());




    }


    public static class MyViewholder extends RecyclerView.ViewHolder {

        TextView Stitle, Sdate, Stime, Svenue;


        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            Stitle =(TextView) itemView.findViewById(R.id.stitle);
            Sdate = (TextView)itemView.findViewById(R.id.sdate);
            Stime = (TextView) itemView.findViewById(R.id.stime);
            Svenue = (TextView) itemView.findViewById(R.id.svenue);


        }
    }
}
package com.miniproject.myapp.student;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.miniproject.myapp.coordinator.EventData;
import com.miniproject.myapp.R;


public class NotificationViewAdapter extends FirebaseRecyclerAdapter<EventPageViewData, NotificationViewAdapter.MyViewholder> {

    /**
     * Initialize a {@link Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public NotificationViewAdapter(@NonNull FirebaseRecyclerOptions<EventPageViewData> options) {
        super(options);
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notificationview,parent,false);
        return new MyViewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewholder holder,  int position, @NonNull EventPageViewData model) {

        holder.Ntitle.setText(model.getEventtitle());
        holder.Ndate.setText(model.getEventdate());
        holder.Ntime.setText(model.getEventtime());
        holder.Nvenue.setText(model.getEventvenue());
        holder.join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("studentjoinedevents");
                String name = holder.Ntitle.getText().toString();
                String date = holder.Ndate.getText().toString();
                String time = holder.Ntime.getText().toString();
                String venue = holder.Nvenue.getText().toString();
                EventData ed = new EventData(name,date,time,venue);
                databaseReference.push().setValue(ed);
                Toast.makeText(holder.join.getContext(), "Event Joined Successfully", Toast.LENGTH_LONG).show();
                FirebaseDatabase.getInstance().getReference().child("Notificationevents")
                        .child(getRef(position).getKey()).removeValue();


            }
        });


    }




    public static class MyViewholder extends RecyclerView.ViewHolder {

        TextView Ntitle, Ndate, Ntime, Nvenue;
        Button join;


        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            Ntitle =(TextView) itemView.findViewById(R.id.Ntitle);
            Ndate = (TextView)itemView.findViewById(R.id.Ndate);
            Ntime = (TextView) itemView.findViewById(R.id.Ntime);
            Nvenue = (TextView) itemView.findViewById(R.id.Nvenue);
            join = (Button) itemView.findViewById(R.id.Join);


        }
    }
}
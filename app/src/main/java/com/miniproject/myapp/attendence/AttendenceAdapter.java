package com.miniproject.myapp.attendence;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.miniproject.myapp.R;



public class AttendenceAdapter extends FirebaseRecyclerAdapter<AttendenceData, AttendenceAdapter.MyViewholder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AttendenceAdapter(@NonNull FirebaseRecyclerOptions<AttendenceData> options) {
        super(options);
    }

    @NonNull
    @Override
    public AttendenceAdapter.MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attencendeviewitem,parent,false);
        return new MyViewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull AttendenceAdapter.MyViewholder holder, int position, @NonNull AttendenceData model) {
        holder.Studentid.setText(model.getStudentRollId());
    }



    public static class MyViewholder extends RecyclerView.ViewHolder {
        TextView Studentid;
        public MyViewholder(@NonNull View itemView) {
            super(itemView);
             Studentid =  itemView.findViewById(R.id.RollID);
        }
    }
}

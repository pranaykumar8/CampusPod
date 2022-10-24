package com.miniproject.myapp.coordinator;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import com.miniproject.myapp.R;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Adapter extends FirebaseRecyclerAdapter<EventData,Adapter.MyViewholder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Adapter(@NonNull FirebaseRecyclerOptions<EventData> options) {
        super(options);
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eventitem,parent,false);
        return new MyViewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewholder holder, int position, @NonNull EventData model) {

        holder.Etitle.setText(model.getEventtitle());
        holder.Edate.setText(model.getEventdate());
        holder.Etime.setText(model.getEventtime());
        holder.Evenue.setText(model.getEventvenue());

        holder.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.Etitle.getContext())
                        .setContentHolder(new ViewHolder((R.layout.updateeventdata)))
                        .setExpanded(true,1500)
                        .create();

                View view = dialogPlus.getHolderView();
                EditText Etitle = view.findViewById(R.id.upadteEtitle);
                EditText Edate = view.findViewById(R.id.upadteEdate);
                EditText Etime = view.findViewById(R.id.upadteEtime);
                EditText Evenue = view.findViewById(R.id.upadteEvenue);

                Button updatedata = (Button) view.findViewById(R.id.updatedata);

                Etitle.setText(model.getEventtitle());
                Edate.setText(model.getEventdate());
                Etime.setText(model.getEventtime());
                Evenue.setText(model.getEventvenue());
                dialogPlus.show();
                updatedata.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("eventtitle",Etitle.getText().toString());
                        map.put("eventdate",Edate.getText().toString());
                        map.put("eventtime",Etime.getText().toString());
                        map.put("eventvenue",Evenue.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("events")
                                .child(Objects.requireNonNull(getRef(position).getKey())).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.Etitle.getContext(), "Data Upadated Successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }

                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.Etitle.getContext(), "Data Upadated Failed", Toast.LENGTH_SHORT).show();

                                    }
                                });

                    }
                });


            }
        });
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.Etitle.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted can't be undo.");
                builder.setPositiveButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("events")
                                .child(getRef(position).getKey()).removeValue();
                        Toast.makeText(holder.Etitle.getContext(), "event deleted successfully.", Toast.LENGTH_SHORT).show();


                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.Etitle.getContext(), "cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

    }



    public static class MyViewholder extends RecyclerView.ViewHolder {

        TextView Etitle, Edate, Etime, Evenue;
        Button btnupdate,btndelete;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            Etitle =(TextView) itemView.findViewById(R.id.title);
            Edate = (TextView)itemView.findViewById(R.id.date);
            Etime = (TextView) itemView.findViewById(R.id.time);
            Evenue = (TextView) itemView.findViewById(R.id.venue);

            btnupdate = (Button) itemView.findViewById(R.id.update);
            btndelete  =(Button) itemView.findViewById(R.id.delete);
        }
    }
}
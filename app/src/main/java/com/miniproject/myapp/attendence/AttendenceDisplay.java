package com.miniproject.myapp.attendence;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.miniproject.myapp.R;
import com.miniproject.myapp.student.EventPageViewData;
import com.miniproject.myapp.student.EventViewPageAdapter;

public class AttendenceDisplay extends AppCompatActivity {
    RecyclerView recyclerView;
    AttendenceAdapter attendenceAdapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence_display);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        recyclerView =  findViewById(R.id.AttendenceRecyclerView);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Attendence");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AttendenceDisplay.this));

        FirebaseRecyclerOptions<AttendenceData> options =
                new FirebaseRecyclerOptions.Builder<AttendenceData>()
                        .setQuery(databaseReference, AttendenceData.class)
                        .build();

        attendenceAdapter =new AttendenceAdapter(options);
        recyclerView.setAdapter(attendenceAdapter);

    }
    @Override
    protected void onStart() {
        super.onStart();
        attendenceAdapter.startListening();

    }


    @Override
    protected void onStop() {
        super.onStop();
        attendenceAdapter.stopListening();
    }
}
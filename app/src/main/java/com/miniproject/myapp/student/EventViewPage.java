package com.miniproject.myapp.student;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.miniproject.myapp.R;


public class EventViewPage extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    EventViewPageAdapter eventViewPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view_page);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        recyclerView = findViewById(R.id.recyclerview2);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("studentjoinedevents");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<EventPageViewData> options1 =
                new FirebaseRecyclerOptions.Builder<EventPageViewData>()
                        .setQuery(databaseReference, EventPageViewData.class)
                        .build();

        eventViewPageAdapter =new EventViewPageAdapter(options1);
        recyclerView.setAdapter(eventViewPageAdapter);


        ImageView backtoSdashboard = (ImageView) findViewById(R.id.backtoSdashboard);
        backtoSdashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StudentDashboard.class));
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        eventViewPageAdapter.startListening();

    }


    @Override
    protected void onStop() {
        super.onStop();
        eventViewPageAdapter.stopListening();
    }
}
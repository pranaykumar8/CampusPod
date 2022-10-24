package com.miniproject.myapp.student;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.miniproject.myapp.R;


public class NotificationPage extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    NotificationViewAdapter notificationViewAdapter;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_page);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        recyclerView = findViewById(R.id.recyclerview3);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Notificationevents");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<EventPageViewData> options1 =
                new FirebaseRecyclerOptions.Builder<EventPageViewData>()
                        .setQuery(databaseReference, EventPageViewData.class)
                        .build();

        notificationViewAdapter =new NotificationViewAdapter(options1);
        recyclerView.setAdapter(notificationViewAdapter);



        ImageView backtoStdashboard = (ImageView) findViewById(R.id.backtoStdashboard);
        backtoStdashboard.setOnClickListener(new View.OnClickListener() {
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
        notificationViewAdapter.startListening();

    }


    @Override
    protected void onStop() {
        super.onStop();
        notificationViewAdapter.stopListening();
    }



}
package com.miniproject.myapp.student;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miniproject.myapp.AskUserActivity;
import com.miniproject.myapp.Navigation.Directions;
import com.miniproject.myapp.Navigation.MapsActivity;
import com.miniproject.myapp.R;
import com.miniproject.myapp.UserProfile;
import com.miniproject.myapp.attendence.Attendence;
import com.miniproject.myapp.attendence.AttendenceDisplay;
import com.miniproject.myapp.user;


public class StudentDashboard extends AppCompatActivity {
    private ImageButton logoutbtn,eventsbtn,notificationbtn,Attendence,accountbtn;
    TextView Name,Id;
    String UserID;
    ImageView profilephoto;
    FirebaseUser user;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Name = (TextView) findViewById(R.id.NAME) ;
        Id = (TextView) findViewById(R.id.StudentRollID);
        profilephoto = (ImageView) findViewById(R.id.profilephoto);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Students");
        UserID = user.getUid();

        databaseReference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile user = snapshot.getValue(UserProfile.class);
                if(user!=null){
                    String fullname = user.fullname;
                    String id = user.collegeid;
                    String pic = user.profile;
                    Name.setText(fullname);
                    Id.setText(id);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        eventsbtn = (ImageButton) findViewById(R.id.Events);
        eventsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EventViewPage.class));
            }
        });
        notificationbtn=(ImageButton) findViewById(R.id.Notification);
        notificationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NotificationPage.class));

            }
        });


        logoutbtn =(ImageButton) findViewById(R.id.Logout);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendalert();
            }
        });


        Attendence = findViewById(R.id.Attendence);
        Attendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AttendenceDisplay.class));
            }
        });

        accountbtn = (ImageButton) findViewById(R.id.Account);
        accountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),StudentDashboardAccount.class);
//                intent.putExtra("UserID",UserID);
                startActivity(intent);
            }
        });

    }

    private void sendalert() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to Logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        StudentDashboard.super.onBackPressed();
                        startActivity(new Intent(getApplicationContext(), AskUserActivity.class));
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Handle the back button
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            //Ask the user if they want to quit
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Exit")
                    .setMessage("Do you want to really Exit?")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //Stop the activity
                            StudentDashboard.this.finish();
                            finishAffinity();
                        }
                    })
                    .setNegativeButton("no", null)
                    .show();

            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }

    }
}
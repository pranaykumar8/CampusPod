package com.miniproject.myapp.coordinator;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miniproject.myapp.AskUserActivity;
import com.miniproject.myapp.R;
import com.miniproject.myapp.UserProfile;
import com.miniproject.myapp.attendence.Attendence;
import com.miniproject.myapp.attendence.AttendenceDisplay;


public class CoordinatorDashboard extends AppCompatActivity implements View.OnClickListener {

    ImageButton createevent,showevents,logout,attendenceBtn,attendence_register,AccountBtn;
    TextView Name,Id;
    String UserID;
    FirebaseUser user;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_dashboard);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Coordinators");
        UserID =user.getUid();

        Name = (TextView) findViewById(R.id.Name) ;
        Id = (TextView) findViewById(R.id.ID);
        databaseReference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile user = snapshot.getValue(UserProfile.class);
                if(user!=null){
                    String fullname = user.fullname;
                    String id = user.collegeid;
                    Name.setText(fullname);
                    Id.setText(id);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        createevent =(ImageButton) findViewById(R.id.neweventbtn);
        createevent.setOnClickListener(this);
        showevents = findViewById(R.id.eventsbtn);
        showevents.setOnClickListener(this);
        attendenceBtn = (ImageButton) findViewById(R.id.AttendenceBtn);
        attendenceBtn.setOnClickListener(this);
        logout = findViewById(R.id.logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendalert();

            }
        });
        attendence_register = findViewById(R.id.AttendenceRegister);
        attendence_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AttendenceDisplay.class));
            }
        });
        AccountBtn = (ImageButton) findViewById(R.id.AccountBtn);
        AccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CoordinatorDashboardAccount.class));
            }
        });

    }

    private void sendalert() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to Logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CoordinatorDashboard.super.onBackPressed();
                        startActivity(new Intent(getApplicationContext(), AskUserActivity.class));
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.neweventbtn) {
            startActivity(new Intent(getApplicationContext(), CreateEventPage.class));
        }
        else if (view.getId() == R.id.eventsbtn){
            startActivity(new Intent(getApplicationContext(), EventsPage.class));
        }
        else if (view.getId() == (R.id.AttendenceBtn)){
            startActivity(new Intent(getApplicationContext(), Attendence.class));
        }


    }
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
                            CoordinatorDashboard.this.finish();
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
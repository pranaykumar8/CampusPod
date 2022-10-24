package com.miniproject.myapp.coordinator;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.miniproject.myapp.AskUserActivity;
import com.miniproject.myapp.MainActivity;
import com.miniproject.myapp.R;


public class CoordinatorDashboard extends AppCompatActivity implements View.OnClickListener {

    Button createevent,showevents,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_dashboard);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        createevent =(Button) findViewById(R.id.neweventbtn);
        createevent.setOnClickListener(this);
        showevents = findViewById(R.id.eventsbtn);
        showevents.setOnClickListener(this);
        logout = findViewById(R.id.LogoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendalert();

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
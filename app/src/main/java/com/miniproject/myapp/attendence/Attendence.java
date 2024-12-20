package com.miniproject.myapp.attendence;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.miniproject.myapp.R;
import com.miniproject.myapp.coordinator.CoordinatorDashboard;
import com.miniproject.myapp.student.EventPageViewData;
import com.miniproject.myapp.student.EventViewPageAdapter;

import java.util.Arrays;
import java.util.Locale;

public class Attendence extends AppCompatActivity {

    private String STUDENT;
    DatabaseReference Dref;
    Button scan;
    ImageButton back;
    AttendenceData ad;
    RecyclerView recyclerView;
    AttendenceAdapter attendenceAdapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Dref = FirebaseDatabase.getInstance().getReference().child("Attendence");

        back = findViewById(R.id.backtoCdashboard);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CoordinatorDashboard.class));
            }
        });


        scan = (Button) findViewById(R.id.ScanBtn);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scancode();

            }
        });


    }

    private void scancode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scanner");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(BarcodeScanner.class);
        barLauncher.launch(options);
    }


    ActivityResultLauncher<ScanOptions> barLauncher =registerForActivityResult(new ScanContract(),result -> {
        if(result.getContents()!=null){
            AlertDialog.Builder builder = new AlertDialog.Builder(Attendence.this);
            builder.setTitle("Result");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Dref = FirebaseDatabase.getInstance().getReference().child("Attendence");
                    String student = result.getContents().toUpperCase(Locale.ROOT);
                    ad= new AttendenceData(student);
                    Dref.push().setValue(ad);
                    Toast.makeText(Attendence.this, "Student attendence is captured", Toast.LENGTH_SHORT).show();

                }
            }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(getApplicationContext(),Attendence.class));
                    Toast.makeText(Attendence.this, "Scan Cancelled", Toast.LENGTH_SHORT).show();
                }
            }).show();

        }
    });




}




package com.miniproject.myapp.coordinator;



import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;



import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.miniproject.myapp.R;


import java.text.SimpleDateFormat;
import java.util.Calendar;




public class CreateEventPage extends AppCompatActivity {
    EditText eventname,eventdate,eventtime,eventvenue;
    int PLACE_PICKER_REQUEST=1;
    private static final String CHANNEL_ID="CampusDrive";
    private static final  int NOTIFICATION_ID=100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event_page);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        eventname = (EditText) findViewById(R.id.EventnameET);
        eventdate = findViewById(R.id.EventdateET);
        eventdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(eventdate);
            }
        });
        eventtime = (EditText) findViewById(R.id.EventtimeET);
        eventtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(eventtime);
            }
        });
        eventvenue = (EditText) findViewById(R.id.EventvenueET);

        Button add = (Button) findViewById(R.id.ADD);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("events");
                String name = eventname.getText().toString();
                String date = eventdate.getText().toString();
                String time = eventtime.getText().toString();
                String venue = eventvenue.getText().toString();
                EventData ed = new EventData(name,date,time,venue);
                databaseReference.push().setValue(ed);
                Toast.makeText(CreateEventPage.this, "Event added successully", Toast.LENGTH_LONG).show();

                DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Notificationevents");
                String nname = eventname.getText().toString();
                String ndate = eventdate.getText().toString();
                String ntime = eventtime.getText().toString();
                String nvenue = eventvenue.getText().toString();
                EventData ned = new EventData(nname,ndate,ntime,nvenue);
                databaseReference2.push().setValue(ned);

                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification;
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
                    notification= new Notification.Builder(CreateEventPage.this)
                            .setSmallIcon(R.drawable.eventlogo)
                            .setContentText(nname+" Event Notification From Vbit scheduled on"+" "+ndate+"at"+" "+ntime)
                            .setSubText("New Event Notification")
                            .setChannelId(CHANNEL_ID)
                            .build();
                    nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID,"CampusDrive",NotificationManager.IMPORTANCE_DEFAULT));
                }else{
                    notification= new Notification.Builder(CreateEventPage.this)
                            .setSmallIcon(R.drawable.eventlogo)
                            .setContentText(nname+" Event Notification From Vbit scheduled on"+ndate+"at"+ntime)
                            .setSubText("New Event Notification")
                            .build();
                }
                nm.notify(NOTIFICATION_ID, notification);



                startActivity(new Intent(getApplicationContext(), EventsPage.class));
            }
        });

    }

    private void showTimeDialog(final EditText eventtime) {
        final Calendar calendar=Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm a");
                eventtime.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        new TimePickerDialog(CreateEventPage.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
    }
    private void showDateDialog(final EditText eventdate) {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yy");
                eventdate.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };

        new DatePickerDialog(CreateEventPage.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


}













package com.miniproject.myapp.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miniproject.myapp.R;
import com.miniproject.myapp.UserProfile;

import java.util.HashMap;

public class StudentDashboardAccount extends AppCompatActivity {
    TextView name_section_name;
    TextView RollID_Section_ID;
    TextView Email_section_email;
    TextView Password_Section_password;
    DatabaseReference databaseReference,databaseReference2;
    Button save,edit,update;
    FirebaseUser user;
    String UserID,fullname,id,email,pass,ufullname,uid,uemail,upass;
    private Bitmap bit = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard_account);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        name_section_name = (TextView) findViewById(R.id.name_section_name);
        RollID_Section_ID = findViewById(R.id.RollID_Section_ID);
        Email_section_email = findViewById(R.id.Email_section_email);
        Password_Section_password = findViewById(R.id.Password_Section_password);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Students");
        UserID = user.getUid();

        databaseReference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile user = snapshot.getValue(UserProfile.class);
                if(user!=null){
                    fullname = user.fullname;
                    id = user.collegeid;
                    email = user.email;
                    pass = user.password;
                    name_section_name.setText(fullname);
                    RollID_Section_ID.setText(id);
                    Email_section_email.setText(email);
                    Password_Section_password.setText(pass);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        save = findViewById(R.id.SaveProfile);

        edit=findViewById(R.id.EditProfile);

        update = findViewById(R.id.UpdateProfile);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ufullname = name_section_name.getText().toString();
               uid = RollID_Section_ID.getText().toString();
               uemail = Email_section_email.getText().toString();
               upass = Password_Section_password.getText().toString();
               checkValidation();
            }
        });
        ImageView backtoSdashboard = (ImageView) findViewById(R.id.backtoSdashboard);
        backtoSdashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),StudentDashboard.class));
            }
        });
    }

    private void checkValidation() {
        if(ufullname.equals(fullname) && uid.equals(id) && uemail.equals(email) &&upass.equals(pass)){
            Toast.makeText(this, "EveryThing is Perfect", Toast.LENGTH_SHORT).show();
        }
        else{
            if(ufullname.isEmpty() || uid.isEmpty() || uemail.isEmpty() || upass.isEmpty()){
                name_section_name.requestFocus();
            }
            else{
                updateData();
            }

        }
    }

    private void updateData() {
        HashMap hp = new HashMap();
        hp.put("fullname",ufullname);
        hp.put("collegeid",uid);
        hp.put("email",uemail);
        hp.put("password",upass);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updatePassword(upass)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            databaseReference2= FirebaseDatabase.getInstance().getReference().child("Students");
                            databaseReference2.child(UserID).updateChildren(hp).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if(task.isSuccessful()){

                                        databaseReference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                UserProfile user = snapshot.getValue(UserProfile.class);
                                                if(user!=null){
                                                    fullname = user.fullname;
                                                    id = user.collegeid;
                                                    email = user.email;
                                                    pass = user.password;
                                                    name_section_name.setText(fullname);
                                                    RollID_Section_ID.setText(id);
                                                    Email_section_email.setText(email);
                                                    Password_Section_password.setText(pass);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                        Toast.makeText(StudentDashboardAccount.this, "Data updated", Toast.LENGTH_SHORT).show();


                                    }else{
                                        Toast.makeText(StudentDashboardAccount.this, "Failed to update", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(StudentDashboardAccount.this, "update failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


}
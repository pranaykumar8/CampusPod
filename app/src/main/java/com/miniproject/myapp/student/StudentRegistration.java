package com.miniproject.myapp.student;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.miniproject.myapp.R;
import com.miniproject.myapp.user;


public class StudentRegistration extends AppCompatActivity implements View.OnClickListener {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://eventnavigationapp-b92e6-default-rtdb.firebaseio.com/");
    private EditText studentname, stdcollegeid, create_password, stdemail;
    private Button register;
    private ImageView backtoStudentloginpage;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAuth = FirebaseAuth.getInstance();
        backtoStudentloginpage = findViewById(R.id.backtostudentloginpage);
        backtoStudentloginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentRegistration.this, StudentLogin.class));
            }
        });
        register = findViewById(R.id.registeruser);
        register.setOnClickListener(this);
        studentname = findViewById(R.id.newStudent);
        stdcollegeid = findViewById(R.id.studentid);
        create_password = findViewById(R.id.createpassword);
        stdemail = findViewById(R.id.studentemail);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registeruser:
                register();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            //
        }

    }

    private void register() {
        progressBar.setVisibility(View.GONE);
        String name = studentname.getText().toString().trim();
        String id = stdcollegeid.getText().toString().trim();
        String Cpassword = create_password.getText().toString().trim();
        String Email = stdemail.getText().toString().trim();

        if (name.isEmpty()) {
            studentname.setError("Full Name is required");
            studentname.requestFocus();
            return;
        }
        if (id.isEmpty()) {
            stdcollegeid.setError("ID is required");
            stdcollegeid.requestFocus();
            return;
        }
        if (Email.isEmpty()) {
            stdemail.setError("Email is required");
            stdemail.requestFocus();
            return;
        }
        if (Cpassword.isEmpty()) {
            create_password.setError("Password is required");
            create_password.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            stdemail.setError("please a valid email ");
            stdemail.requestFocus();
            return;
        }
        if (Cpassword.length() < 6) {
            create_password.setError("please provide min 8 characters");
            create_password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(Email,Cpassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            user u = new user(name,id,Email,Cpassword);
                            FirebaseDatabase.getInstance().getReference("Students")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressBar.setVisibility(View.GONE);
                                            if(task.isSuccessful()){
                                                Toast.makeText(StudentRegistration.this,"Register successfull",Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(getApplicationContext(), StudentLogin.class));
                                            }
                                            else{
                                                Toast.makeText(StudentRegistration.this,"Register unsuccessfull",Toast.LENGTH_LONG).show();

                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(StudentRegistration.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }


}

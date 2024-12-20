package com.miniproject.myapp.student;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.miniproject.myapp.AskUserActivity;
import com.miniproject.myapp.R;

import org.checkerframework.checker.units.qual.A;


public class StudentLogin extends AppCompatActivity{
    private DatabaseReference databaseReference;
    private FirebaseUser users;
    private String userID;
    private Button userlogin, newregister;
    private EditText loginusername, loginpassword;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private ImageView backtouseractivity;
    private TextView forgotpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        backtouseractivity = findViewById(R.id.backtouseractivity);
        backtouseractivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentLogin.this, AskUserActivity.class));
            }
        });
        newregister = findViewById(R.id.newregister);
        loginusername = findViewById(R.id.usernametxt);
        loginpassword = findViewById(R.id.passwordtxt);
        userlogin = findViewById(R.id.login);
        mAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progress_bar);
        forgotpassword = findViewById(R.id.forgotpassword);
        newregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), StudentRegistration.class));
            }
        });

        userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),StudentForgotPassword.class));
                finish();
            }
        });
    }



    private void login() {
        String Email= loginusername.getText().toString().trim();
        String Password = loginpassword.getText().toString().trim();

        if(Email.isEmpty()){
            loginusername.setError("Full Name is required");
            loginusername.requestFocus();
            return;
        }
        if(Password.isEmpty()){
            loginpassword.setError("password is required");
            loginpassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
//
                    Toast.makeText(StudentLogin.this, "Login Successfull", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), StudentDashboard.class));

                }
                else{
//
                    Toast.makeText(StudentLogin.this, "Login UnSuccessfull", Toast.LENGTH_LONG).show();

                }
            }
        });

    }


}


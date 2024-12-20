package com.miniproject.myapp.coordinator;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.miniproject.myapp.AskUserActivity;
import com.miniproject.myapp.R;


public class CoordinatorLogin extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseUser users;
    private String userID;
    TextView Cforgotpassword;
    private Button Clogin, Cregister;
    private EditText coordinateuser, coordinatepassword;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private ImageView backtoaskuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_login);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Cregister = findViewById(R.id.new_register);
        backtoaskuser =findViewById(R.id.backtoaskuser);
        backtoaskuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CoordinatorLogin.this, AskUserActivity.class));
            }
        });
        coordinateuser = findViewById(R.id.Cusernametxt);
        coordinatepassword = findViewById(R.id.Cpasswordtxt);
        Clogin = findViewById(R.id.Clogin);
        mAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progress_bar);
        Cregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), CoordinatorRegistration.class));
            }
        });

        Clogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        Cforgotpassword = findViewById(R.id.Cforgotpassword);
        Cforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CoordinatorForgotPassword.class));
                finish();
            }
        });
    }
    private void login() {
        String CEmail= coordinateuser.getText().toString().trim();
        String CPassword = coordinatepassword.getText().toString().trim();

        if(CEmail.isEmpty()){
            coordinateuser.setError("Full Name is required");
            coordinateuser.requestFocus();
            return;
        }
        if(CPassword.isEmpty()){
            coordinatepassword.setError("password is required");
            coordinatepassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(CEmail,CPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
//
                    Toast.makeText(CoordinatorLogin.this, "Login Successfull", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), CoordinatorDashboard.class));

                }
                else{
//
                    Toast.makeText(CoordinatorLogin.this, "Login UnSuccessfull", Toast.LENGTH_LONG).show();

                }
            }
        });

    }
}
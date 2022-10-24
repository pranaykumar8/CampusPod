package com.miniproject.myapp.coordinator;



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

public class CoordinatorRegistration extends AppCompatActivity implements View.OnClickListener  {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://eventnavigationapp-b92e6-default-rtdb.firebaseio.com/");
    private EditText coordinatename, coordinateid,coordinatecreate_password, coordinateEmail;
    private Button coordinateregister;
    private ProgressBar progressBar;
    private ImageView backtocoordinateloginpage;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_registration);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mAuth = FirebaseAuth.getInstance();

        backtocoordinateloginpage = findViewById(R.id.backtocoordinateloginpage);
        backtocoordinateloginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CoordinatorRegistration.this,CoordinatorLogin.class));
            }
        });
        coordinateregister = findViewById(R.id.RegisterCoordinator);
        coordinateregister.setOnClickListener(this);
        coordinateregister.setOnClickListener(this);
        coordinatename = findViewById(R.id.CoordinatorName);
        coordinateid = findViewById(R.id.CoordinatorID);
        coordinatecreate_password = findViewById(R.id.CoordinatorCreatePassword);
        coordinateEmail = findViewById(R.id.CoordinatorEmail);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);


    }
    @Override
    public void onClick(View v) {

        register();

    }


    private void register() {
        progressBar.setVisibility(View.GONE);
        String Cname = coordinatename.getText().toString().trim();
        String Cid = coordinateid.getText().toString().trim();
        String Cpassword = coordinatecreate_password.getText().toString().trim();
        String Cemail = coordinateEmail.getText().toString().trim();

        if (Cname.isEmpty()) {
            coordinatename.setError("Full Name is required");
            coordinatename.requestFocus();
            return;
        }
        if (Cid.isEmpty()) {
            coordinateid.setError("ID is required");
            coordinateid.requestFocus();
            return;
        }
        if (Cemail.isEmpty()) {
            coordinateEmail.setError("Email is required");
            coordinateEmail.requestFocus();
            return;
        }
        if (Cpassword.isEmpty()) {
            coordinatecreate_password.setError("Password is required");
            coordinatecreate_password.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Cemail).matches()) {
            coordinateEmail.setError("please a valid email ");
            coordinateEmail.requestFocus();
            return;
        }
        if (Cpassword.length() < 6) {
            coordinatecreate_password.setError("please provide min 8 characters");
            coordinatecreate_password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(Cemail,Cpassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            user u = new user(Cname,Cid,Cemail,Cpassword);
                            FirebaseDatabase.getInstance().getReference("Coordinators")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressBar.setVisibility(View.GONE);
                                            if(task.isSuccessful()){
                                                Toast.makeText(CoordinatorRegistration.this,"Register successfull",Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(getApplicationContext(), CoordinatorLogin.class));
                                            }
                                            else{
                                                Toast.makeText(CoordinatorRegistration.this,"Register unsuccessfull",Toast.LENGTH_LONG).show();

                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(CoordinatorRegistration.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}
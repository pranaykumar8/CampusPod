package com.miniproject.myapp.coordinator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.miniproject.myapp.R;
import com.miniproject.myapp.student.StudentForgotPassword;

public class CoordinatorForgotPassword extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_forgot_password);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAuth= FirebaseAuth.getInstance();
        EditText forgotEmail = (EditText) findViewById(R.id.CforgotEmail);
        Button reset = (Button) findViewById(R.id.Creset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = forgotEmail.getText().toString();
                if( email.equals("")){
                    Toast.makeText(CoordinatorForgotPassword.this, "Cannot be Empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(CoordinatorForgotPassword.this, "Please check your email", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), Log.class));
                                        finish();
                                    }else{
                                        Toast.makeText(CoordinatorForgotPassword.this, "error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });
    }
}
package com.miniproject.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    CharSequence charSequence;
    TextView textView;
    int index;
    Handler handler = new Handler();
    long delay =200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        textView = findViewById(R.id.splashText);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this,AskUserActivity.class);
                startActivity(intent);
                finish();
            }
        },4000);
        animatedText("CAMPUS POD");
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            textView.setText(charSequence.subSequence(0,index++));
            if (index <= charSequence.length()){
                handler.postDelayed(runnable,delay);
            }
        }
    };
    public void animatedText(CharSequence cs){
        charSequence =cs;
        index =0;
        textView.setText("");
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable,delay);
    }
}
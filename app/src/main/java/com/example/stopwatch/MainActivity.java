package com.example.stopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

   private int second;
   private boolean running;
   private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) {
        savedInstanceState.getInt("second");
        savedInstanceState.getBoolean("running");
        savedInstanceState.getBoolean("wasRunning");
        }

        runTimer();

    }

    public void onClickStart(View view){
        running = true;
        Toast.makeText(this, "Start", Toast.LENGTH_SHORT).show();
    }
    public void onClickStop(View view){
        running = false;
        Toast.makeText(this, "Stop", Toast.LENGTH_SHORT).show();
    }
    public void onClickReset(View view){
        running = false;
        second = 0;
        Toast.makeText(this, "Reset", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();

        wasRunning = running;
        running = false;

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(wasRunning){
            running = true;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("second",second);
        outState.putBoolean("running",running);
        outState.putBoolean("wasRunning",wasRunning);

    }
    private void runTimer() {

        TextView textView = findViewById(R.id.Time);
        Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = second/3600;
                int minutes = (second%3600)/60;
                int seconds = second%60;

                String time = String.format(Locale.getDefault(),"%02d:%02d:%02d",hours,minutes,seconds);
                textView.setText(time);

                if(running){
                    second++;
                }
                handler.postDelayed(this,1000);

            }
        });
    }
}
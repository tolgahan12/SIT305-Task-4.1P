package com.example.task41workouttimerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Chronometer chronometer1;
    Chronometer chronometer2;

    boolean running;
    long pauseTime;

    EditText sessionInfoTextView;
    TextView previousSessionTextView1;
    TextView previousSessionTextView2;

    String WORK_OUT;
    String WORK_OUT_TIME;
    long time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set timer
        chronometer1 = findViewById(R.id.chronometer1);
        chronometer2 = findViewById(R.id.chronometer2);

        //Get entered workout
        sessionInfoTextView = findViewById(R.id.sessionInfoTextView);
        //previous workout
        previousSessionTextView2 = findViewById(R.id.previousSessionTextView2);

        if (savedInstanceState != null)
        {
            /*
            //Set workout from previous session
            String previous;
            previous = savedInstanceState.getString(WORK_OUT);
            Editable editable = new SpannableStringBuilder(previous);
            previousSessionTextView2.setText("on " + editable + " last time");
             */
            //set time
            long currentTime;
            currentTime = savedInstanceState.getLong(WORK_OUT_TIME);

            chronometer2.setBase(SystemClock.elapsedRealtime() - (SystemClock.elapsedRealtime() - currentTime));
            chronometer2.start();
            running = true;
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save workout
        //outState.putString(WORK_OUT, sessionInfoTextView.getText().toString());

        //Save workout time
        outState.putLong(WORK_OUT_TIME, time);
    }

    public void startButtonClick(View view) {
        switch (view.getId())
        {
            case R.id.playButton:
                //play
                if (!running){
                    chronometer2.setBase(SystemClock.elapsedRealtime() - pauseTime);
                    chronometer2.start();
                    running = true;

                    time = chronometer2.getBase();
                }
                break;

            case R.id.pauseButton:
                //pause
                if (running){
                    chronometer2.stop();
                    pauseTime = SystemClock.elapsedRealtime() - chronometer2.getBase();
                    running = false;
                }
                break;

            case R.id.stopButton:
                //stop
                //Get previous time and set
                chronometer1.setBase(SystemClock.elapsedRealtime() - (SystemClock.elapsedRealtime() - chronometer2.getBase()));

                //Set time to zero
                chronometer2.setBase(SystemClock.elapsedRealtime());
                pauseTime = 0;

                //set last workout
                Editable workout;
                workout = sessionInfoTextView.getText();
                previousSessionTextView2.setText("on " + workout + " last time");
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());

        }

    }
}
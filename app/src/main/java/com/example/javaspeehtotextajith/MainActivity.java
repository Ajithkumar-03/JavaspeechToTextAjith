package com.example.javaspeehtotextajith;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    String TAG = MainActivity.class.getSimpleName();
    TextView textViewSpeechToText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "-------------------->onCreate");

        textViewSpeechToText = findViewById(R.id.textView);
    }

    ActivityResultLauncher<Intent> activityResultLauncher =registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();

                        ArrayList<String> extraResult = data.getStringArrayListExtra(
                                RecognizerIntent.EXTRA_RESULTS);
                        Log.d(TAG,"-------------------->ReceivedData:" + Objects.requireNonNull(extraResult.get(0)));

                        textViewSpeechToText.setText(Objects.requireNonNull(extraResult).get(0));

                    }
                }
            });


    public void speechToTextMethod(View view) {
        Log.d(TAG,"-------------------->speechToTextMethod");

        Intent myIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        myIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        myIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        activityResultLauncher.launch(Intent.createChooser(myIntent,"speech input"));

    }
}
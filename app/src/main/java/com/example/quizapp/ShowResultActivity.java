package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_result_layout);

        // Get the score and name from QuizActivity
        int score = getIntent().getIntExtra("SCORE", 0);
        String name = getIntent().getStringExtra("NAME");

        // Display the score
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText("Your Score: " + score + "/5");

        // Display a congratulatory message
        TextView congratulationsTextView = findViewById(R.id.congratulationsTextView);
        congratulationsTextView.setText("Congratulations, " + name + "!");

        // Set up button click listeners
        Button closeAppButton = findViewById(R.id.closeAppButton);
        closeAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the app
                finishAffinity();
            }
        });

        Button startNewQuizButton = findViewById(R.id.startNewQuizButton);
        startNewQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start a new quiz (go back to MainActivity or QuizActivity)
                Intent intent = new Intent(ShowResultActivity.this, MainActivity.class); // Change MainActivity.class to your quiz activity class
                startActivity(intent);
                finish();
            }
        });
    }
}

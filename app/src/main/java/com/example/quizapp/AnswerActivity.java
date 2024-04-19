package com.example.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ProgressBar;

public class AnswerActivity extends AppCompatActivity {
    private TextView questionTextView;
    private ProgressBar progressBar;
    private TextView questionNumberTextView;
    private Button nextButton, option1Button, option2Button, option3Button;
    private String[] questions; // Declare questions array
    private int score = 0; // Variable to store the score

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        // Initialize views
        questionTextView = findViewById(R.id.questionTextView);
        nextButton = findViewById(R.id.nextButton);
        option1Button = findViewById(R.id.option1Button);
        option2Button = findViewById(R.id.option2Button);
        option3Button = findViewById(R.id.option3Button);
        progressBar = findViewById(R.id.progressBar);
        questionNumberTextView = findViewById(R.id.questionNumberTextView);

        // Retrieve data from Intent extras
        Intent intent = getIntent();
        String question = intent.getStringExtra("QUESTION");
        String selectedOption = intent.getStringExtra("SELECTED_OPTION");
        String correctAnswer = intent.getStringExtra("CORRECT_ANSWER");
        final int currentQuestionIndex = intent.getIntExtra("CURRENT_QUESTION_INDEX", 0); // Retrieve the current question index

        // Retrieve the questions array from intent extras
        questions = intent.getStringArrayExtra("QUESTIONS_ARRAY");
        int totalQuestions = questions.length; // Total number of questions

        // Retrieve the current score from Intent extras
        score = intent.getIntExtra("SCORE", 0);

        // Set question text
        questionTextView.setText("Question: " + question);

        // Set options text
        option1Button.setText(intent.getStringExtra("OPTION_1"));
        option2Button.setText(intent.getStringExtra("OPTION_2"));
        option3Button.setText(intent.getStringExtra("OPTION_3"));

        // Set question number text
        String questionNumberText = "" + (currentQuestionIndex + 1) + "/" + totalQuestions;
        questionNumberTextView.setText(questionNumberText);

        // Calculate progress
        int progress = (currentQuestionIndex + 1) * 100 / totalQuestions;
        progressBar.setProgress(progress);
        Log.d("AnswerActivity", "Selected Option: " + selectedOption);
        Log.d("AnswerActivity", "Correct Answer: " + correctAnswer);

        // Check if the selected option is correct
        if (selectedOption.equals(correctAnswer)) {
            // Increment the score if the answer is correct
            score++;
            // Set the background color of the selected option button to green
            if (selectedOption.equals(option1Button.getText().toString())) {
                option1Button.setBackgroundColor(Color.GREEN);
            } else if (selectedOption.equals(option2Button.getText().toString())) {
                option2Button.setBackgroundColor(Color.GREEN);
            } else if (selectedOption.equals(option3Button.getText().toString())) {
                option3Button.setBackgroundColor(Color.GREEN);
            }
        } else {
            // Set the background color of the selected option button to red if wrong
            if (selectedOption.equals(option1Button.getText().toString())) {
                option1Button.setBackgroundColor(Color.RED);
            } else if (selectedOption.equals(option2Button.getText().toString())) {
                option2Button.setBackgroundColor(Color.RED);
            } else if (selectedOption.equals(option3Button.getText().toString())) {
                option3Button.setBackgroundColor(Color.RED);
            }

            // Set the background color of the correct option button to green
            if (correctAnswer.equals(option1Button.getText().toString())) {
                option1Button.setBackgroundColor(Color.GREEN);
            } else if (correctAnswer.equals(option2Button.getText().toString())) {
                option2Button.setBackgroundColor(Color.GREEN);
            } else if (correctAnswer.equals(option3Button.getText().toString())) {
                option3Button.setBackgroundColor(Color.GREEN);
            }
        }

        // Log the score after each question
        Log.d("AnswerActivity", "Score after each question: " + score);

        // Set OnClickListener for the Next Question button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextQuestionIndex = currentQuestionIndex + 1;
                if (nextQuestionIndex >= questions.length) {
                    // If it's the last question, start ShowResultActivity
                    Intent intent = new Intent(AnswerActivity.this, ShowResultActivity.class);
                    intent.putExtra("SCORE", score); // Pass the score to ShowResultActivity
                    intent.putExtra("NAME", getIntent().getStringExtra("NAME"));
                    startActivity(intent);
                    finish(); // Finish the current AnswerActivity
                } else {
                    // If there are more questions, start QuizActivity with the next question index
                    Intent intent = new Intent(AnswerActivity.this, QuizActivity.class);
                    intent.putExtra("CURRENT_QUESTION_INDEX", nextQuestionIndex);
                    intent.putExtra("NAME", getIntent().getStringExtra("NAME"));
                    intent.putExtra("SCORE", score); // Pass the score to the next QuizActivity
                    startActivity(intent);
                    finish(); // Finish the current AnswerActivity
                }
            }
        });
    }

}

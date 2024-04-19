
package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {
    private TextView questionTextView;
    private TextView questionNumberTextView; // Add this line
    private int score = 0;
    private ProgressBar progressBar;
    private Button option1Button, option2Button, option3Button, submitButton;
    private Button selectedButton = null;
    private String[] questions = {
            "What is the capital of France?",
            "Who painted the Mona Lisa?",
            "Which planet is known as the Red Planet?",
            "Who is the author of 'To Kill a Mockingbird'?",
            "What is the chemical symbol for water?"
    };

    private String[][] answerChoices = {
            {"Paris", "Tokyo", "Berlin"},
            {"Leonardo da Vinci", "Vincent van Gogh", "Pablo Picasso"},
            {"Mars", "Venus", "Jupiter"},
            {"Harper Lee", "J.K. Rowling", "Stephen King"},
            {"H2O", "CO2", "NaCl"}
    };

    private int currentQuestionIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Initialize views
        questionTextView = findViewById(R.id.questionTextView);
        questionNumberTextView = findViewById(R.id.questionNumberTextView); // Initialize questionNumberTextView
        option1Button = findViewById(R.id.option1Button);
        option2Button = findViewById(R.id.option2Button);
        option3Button = findViewById(R.id.option3Button);
        submitButton = findViewById(R.id.submitButton);
        progressBar = findViewById(R.id.progressBar);

        // Retrieve the current question index from Intent extras
        currentQuestionIndex = getIntent().getIntExtra("CURRENT_QUESTION_INDEX", 0);

        // Display the current question and update progress
        displayQuestion();
        updateProgress();

        // Set OnClickListener for option buttons
        option1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectOption(option1Button);
            }
        });

        option2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectOption(option2Button);
            }
        });

        option3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectOption(option3Button);
            }
        });

        // Set OnClickListener for the Submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAnswerActivity();
            }
        });
    }

    private void displayQuestion() {
        questionTextView.setText(questions[currentQuestionIndex]);
        questionNumberTextView.setText((currentQuestionIndex + 1) + "/" + questions.length); // Update question number

        String[] choices = answerChoices[currentQuestionIndex];
        option1Button.setText(choices[0]);
        option2Button.setText(choices[1]);
        option3Button.setText(choices[2]);
    }

    private void selectOption(Button button) {
        // Reset color of previously selected button, if any
        if (selectedButton != null) {
            resetButtonColor(selectedButton);
        }
        // Highlight selected button
        selectedButton = button;
        selectedButton.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
    }

    private void resetButtonColor(Button button) {
        button.setBackgroundColor(getResources().getColor(android.R.color.transparent)); // Set to transparent or the default color
    }

    private void openAnswerActivity() {
        // Check if an option is selected
        if (selectedButton != null) {
            // Get the selected option
            String selectedOption = getSelectedOption();

            // Get the correct answer for the current question
            String correctAnswer = getCorrectAnswer();

            // Check if the selected option is correct
            boolean isCorrect = selectedOption.equals(correctAnswer);

            // Update score if the answer is correct
            if (isCorrect) {
                score++;
            }

            // Pass question, selected option, correct answer, options, score, and questions array to AnswerActivity
            Intent intent = new Intent(QuizActivity.this, AnswerActivity.class);
            intent.putExtra("QUESTION", questions[currentQuestionIndex]);
            intent.putExtra("SELECTED_OPTION", selectedOption);
            intent.putExtra("CORRECT_ANSWER", correctAnswer);
            intent.putExtra("IS_CORRECT", isCorrect);
            intent.putExtra("OPTION_1", answerChoices[currentQuestionIndex][0]);
            intent.putExtra("OPTION_2", answerChoices[currentQuestionIndex][1]);
            intent.putExtra("OPTION_3", answerChoices[currentQuestionIndex][2]);
            intent.putExtra("SCORE", score); // Pass the score to AnswerActivity
            intent.putExtra("CURRENT_QUESTION_INDEX", currentQuestionIndex); // Pass the current question index
            intent.putExtra("QUESTIONS_ARRAY", questions); // Pass the questions array
            intent.putExtra("NAME", getIntent().getStringExtra("NAME"));
            startActivity(intent);
        } else {
            // Inform the user to select an option
            Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show();
        }
    }
    private String getSelectedOption() {
        if (selectedButton != null) {
            return selectedButton.getText().toString();
        }
        return "";
    }

    private String getCorrectAnswer() {
        // Retrieve the index of the correct answer from the Intent extras
        int correctAnswerIndex = getIntent().getIntExtra("CORRECT_ANSWER_INDEX", 0);

        // Return the correct answer based on the index
        return answerChoices[currentQuestionIndex][correctAnswerIndex];
    }

    private void updateProgress() {
        // Calculate progress and update the ProgressBar
        int progress = (currentQuestionIndex + 1) * 100 / questions.length;
        progressBar.setProgress(progress);
    }
}

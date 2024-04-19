package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) findViewById(R.id.name_editText)).getText().toString();

                // Check if the name is empty
                if (name.trim().isEmpty()) {
                    // If name is empty, prompt the user to enter name
                    Toast.makeText(MainActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                } else {
                    // Start quiz activity and pass name to it
                    Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                    intent.putExtra("NAME", name);
                    startActivity(intent);
                }
            }
        });
    }

}

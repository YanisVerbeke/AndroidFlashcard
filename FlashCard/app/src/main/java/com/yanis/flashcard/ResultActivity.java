package com.yanis.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    int score;
    int totalQuestionNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // get the score and the number of questions from the QuestionActivity
        Intent scoreIntent = getIntent();
        score = scoreIntent.getIntExtra("score", 0);
        totalQuestionNumber = scoreIntent.getIntExtra("totalQuestionNumber", 0);

        // display the score
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText(score + " / " + totalQuestionNumber);

        // back to the main menu
        Button backMenuButton = findViewById(R.id.backMenuButton);
        backMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

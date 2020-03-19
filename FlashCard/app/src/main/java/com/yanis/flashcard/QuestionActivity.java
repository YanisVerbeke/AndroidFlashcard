package com.yanis.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.map_of_the_problematique);
        Button audioButton = findViewById(R.id.audioButton);
        audioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
            }
        });

        Button stopButton = findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
        });

        Button validateButton = findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView correctAnswerTextView = findViewById(R.id.correctAnswerTextView);
                TextView labelAnswerTextView = findViewById(R.id.labelAnswerTextView);
                RadioGroup questionsRadioGroup = findViewById(R.id.questionsRadioGroup);
                RadioButton checkedAnswer = findViewById(questionsRadioGroup.getCheckedRadioButtonId());
                if (((String) checkedAnswer.getText()).equals("Map Of The Problematique")) {
                    Log.i("Answer", "OUI");
                    correctAnswerTextView.setText("Bonne réponse !");
                    labelAnswerTextView.setText("");
                } else {
                    correctAnswerTextView.setText("Mauvaise réponse !");
                    labelAnswerTextView.setText("La bonne réponse était \"Map Of The Problematique\"");
                }
                Log.i("Answer", (String) checkedAnswer.getText());
            }
        });
    }
}

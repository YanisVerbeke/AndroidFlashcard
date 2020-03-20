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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    boolean isAnswering = true;
    int actualQuestion = 0;
    int totalQuestionNumber = 2;
    List<Question> questionList;
    Question question;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);


        questionList = CreateQuestions();
        ResetQuestion();

        final Button validateButton = findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAnswering) {
                    TextView correctAnswerTextView = findViewById(R.id.correctAnswerTextView);
                    TextView labelAnswerTextView = findViewById(R.id.labelAnswerTextView);
                    RadioGroup questionsRadioGroup = findViewById(R.id.questionsRadioGroup);
                    RadioButton checkedAnswer = findViewById(questionsRadioGroup.getCheckedRadioButtonId());
                    if (((String) checkedAnswer.getText()).equals(question.getCorrectAnswer())) {
                        correctAnswerTextView.setText("Bonne réponse !");
                        labelAnswerTextView.setText("");
                    } else {
                        correctAnswerTextView.setText("Mauvaise réponse !");
                        labelAnswerTextView.setText("La bonne réponse était \"" + question.getCorrectAnswer() + "\"");
                    }
                    if (actualQuestion < totalQuestionNumber - 1) {
                        validateButton.setText("Question suivante");
                    } else {
                        validateButton.setText("Voir les résultats");
                    }
                    isAnswering = false;
                } else {
                    mediaPlayer.stop();
                    if (actualQuestion < totalQuestionNumber - 1) {
                        actualQuestion++;
                        ResetQuestion();
                        validateButton.setText("Valider la réponse");
                    } else {

                    }
                }
            }
        });
    }

    private void CreateRadioButton(List<String> answerList) {
        RadioGroup questionRadioGroup = findViewById(R.id.questionsRadioGroup);
        questionRadioGroup.clearCheck();
        RadioButton firstAnswerRadioButton = findViewById(R.id.firstAnswerRadioButton);
        RadioButton secondAnswerRadioButton = findViewById(R.id.secondAnswerRadioButton);
        RadioButton thirdAnswerRadioButton = findViewById(R.id.thirdAnswerRadioButton);
        RadioButton fourthAnswerRadioButton = findViewById(R.id.fourthAnswerRadioButton);
        Collections.shuffle(answerList);
        firstAnswerRadioButton.setText(answerList.get(0));
        secondAnswerRadioButton.setText(answerList.get(1));
        thirdAnswerRadioButton.setText(answerList.get(2));
        fourthAnswerRadioButton.setText(answerList.get(3));
    }

    private List<Question> CreateQuestions() {
        List<Question> questionList = new ArrayList<>();

        questionList.add(new Question(R.raw.map_of_the_problematique, Arrays.asList("Uprising", "Map Of The Problematique", "Bliss", "Psycho"), "Map Of The Problematique"));
        questionList.add(new Question(R.raw.cant_stop, Arrays.asList("Can't Stop", "Californication", "Give It Away", "Under The Bridge"), "Can't Stop"));
        return questionList;
    }

    private void ResetQuestion() {
        isAnswering = true;
        question = questionList.get(actualQuestion);
        CreateRadioButton(question.getAnswerList());
        mediaPlayer = MediaPlayer.create(QuestionActivity.this, question.getAudioId());
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
    }
}

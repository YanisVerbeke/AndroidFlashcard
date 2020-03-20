package com.yanis.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    boolean isAnswering = true;
    int actualQuestion = 0;
    int totalQuestionNumber = 5;
    int score = 0;
    List<Question> questionList;
    Question question;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // Get all question then randomize them and display the first
        questionList = CreateQuestions();
        Collections.shuffle(questionList);
        ResetQuestion();

        final Button validateButton = findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView correctAnswerTextView = findViewById(R.id.correctAnswerTextView);
                TextView labelAnswerTextView = findViewById(R.id.labelAnswerTextView);
                RadioGroup questionsRadioGroup = findViewById(R.id.questionsRadioGroup);
                // If the user is choosing his answer
                if (isAnswering) {
                    // get the checked answer
                    RadioButton checkedAnswer = findViewById(questionsRadioGroup.getCheckedRadioButtonId());
                    // early return
                    if (checkedAnswer == null) {
                        Toast.makeText(QuestionActivity.this, "Entrez une réponse", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // check if the user selected the good answer
                    if (((String) checkedAnswer.getText()).equals(question.getCorrectAnswer())) {
                        correctAnswerTextView.setText("Bonne réponse !");
                        labelAnswerTextView.setText("");
                        score++;
                    } else {
                        correctAnswerTextView.setText("Mauvaise réponse !");
                        labelAnswerTextView.setText("La bonne réponse était \"" + question.getCorrectAnswer() + "\"");
                    }
                    // checked if it's not the last question
                    if (actualQuestion < totalQuestionNumber - 1) {
                        validateButton.setText("Question suivante");
                    } else {
                        validateButton.setText("Voir les résultats");
                    }
                    // change mode to "next question"
                    isAnswering = false;
                } else {
                    // if the user already gives his answer
                    // reset audio and the display of the answer
                    mediaPlayer.stop();
                    correctAnswerTextView.setText("");
                    labelAnswerTextView.setText("");
                    // check if it's not the last question
                    if (actualQuestion < totalQuestionNumber - 1) {
                        actualQuestion++;
                        ResetQuestion();
                        validateButton.setText("Valider la réponse");
                    } else {
                        // if last question, go to the result activity
                        Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
                        intent.putExtra("score", score);
                        intent.putExtra("totalQuestionNumber", totalQuestionNumber);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    // Modify the RadioButton with the four answer of the question
    private void CreateRadioButton(List<String> answerList) {
        RadioGroup questionRadioGroup = findViewById(R.id.questionsRadioGroup);
        // clear the button already checked
        questionRadioGroup.clearCheck();
        RadioButton firstAnswerRadioButton = findViewById(R.id.firstAnswerRadioButton);
        RadioButton secondAnswerRadioButton = findViewById(R.id.secondAnswerRadioButton);
        RadioButton thirdAnswerRadioButton = findViewById(R.id.thirdAnswerRadioButton);
        RadioButton fourthAnswerRadioButton = findViewById(R.id.fourthAnswerRadioButton);
        // randomize the answer's order
        Collections.shuffle(answerList);
        firstAnswerRadioButton.setText(answerList.get(0));
        secondAnswerRadioButton.setText(answerList.get(1));
        thirdAnswerRadioButton.setText(answerList.get(2));
        fourthAnswerRadioButton.setText(answerList.get(3));
    }

    // create the list with all the possible questions
    private List<Question> CreateQuestions() {
        List<Question> questionList = new ArrayList<>();
        questionList.add(new Question(R.raw.map_of_the_problematique, Arrays.asList("Supermassive Black Hole", "Map Of The Problematique", "Bliss", "Starlight"), "Map Of The Problematique"));
        questionList.add(new Question(R.raw.cant_stop, Arrays.asList("Can't Stop", "By The Way", "Give It Away", "Otherside"), "Can't Stop"));
        questionList.add(new Question(R.raw.all_along_the_watchtower, Arrays.asList("Hey Joe", "Voodoo Child", "All Along The Watchtower", "Foxey Lady"), "All Along The Watchtower"));
        questionList.add(new Question(R.raw.ban_all_the_music, Arrays.asList("Wake Up Call", "Trip Switch", "Six Billion", "Ban All The Music"), "Ban All The Music"));
        questionList.add(new Question(R.raw.brianstorm, Arrays.asList("Fluorescent Adolescent", "Brianstorm", "505", "Balaclava"), "Brianstorm"));
        questionList.add(new Question(R.raw.by_the_way, Arrays.asList("Can't Stop", "By The Way", "Give It Away", "Otherside"), "By The Way"));
        questionList.add(new Question(R.raw.citizen_erased, Arrays.asList("Hysteria", "Sunburn", "Citizen Erased", "New Born"), "Citizen Erased"));
        questionList.add(new Question(R.raw.come_on_over, Arrays.asList("Come On Over", "Out Of The Black", "Little Monster", "Ten Tone Skeleton"), "Come On Over"));
        questionList.add(new Question(R.raw.immigrant_song, Arrays.asList("Immigrant Song", "Ramble On", "Stairway To Heaven", "Heartbreaker"), "Immigrant Song"));
        questionList.add(new Question(R.raw.live_like_animals, Arrays.asList("Amsterdam", "Particles", "Sorry", "Live Like Animals"), "Live Like Animals"));
        questionList.add(new Question(R.raw.r_u_mine, Arrays.asList("R U Mine", "Do I Wanna Know?", "Mad Sounds", "Arabella"), "R U Mine"));
        questionList.add(new Question(R.raw.ramble_on, Arrays.asList("Ramble On", "Custard Pie", "Kashmir", "Communication Breakdown"), "Ramble On"));
        return questionList;
    }

    // display a new question after answering to the previous
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

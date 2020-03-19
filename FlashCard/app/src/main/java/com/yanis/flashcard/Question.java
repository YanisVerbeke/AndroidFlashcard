package com.yanis.flashcard;

import java.util.List;

public class Question {
    private int audioId;
    private List<String> answerList;
    private String correctAnswer;

    public Question(int audioId, List<String> answerList, String correctAnswer) {
        this.audioId = audioId;
        this.answerList = answerList;
        this.correctAnswer = correctAnswer;
    }

    public int getAudioId() {
        return audioId;
    }

    public List<String> getAnswerList() {
        return answerList;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}

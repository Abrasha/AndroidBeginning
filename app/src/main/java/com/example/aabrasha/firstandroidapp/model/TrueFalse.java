package com.example.aabrasha.firstandroidapp.model;

/**
 * Created by Andrii Abramov on 6/30/16.
 */
public class TrueFalse {

    private int question;
    private boolean isTrueQuestion;

    public TrueFalse(int question, boolean isTrueQuestion) {
        this.question = question;
        this.isTrueQuestion = isTrueQuestion;
    }

    public int getQuestion() {
        return question;
    }

    public void setQuestion(int question) {
        this.question = question;
    }

    public boolean isTrueQuestion() {
        return isTrueQuestion;
    }

    public void setTrueQuestion(boolean trueQuestion) {
        isTrueQuestion = trueQuestion;
    }
}

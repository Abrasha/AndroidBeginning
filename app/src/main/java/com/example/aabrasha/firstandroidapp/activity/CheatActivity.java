package com.example.aabrasha.firstandroidapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aabrasha.firstandroidapp.R;

public class CheatActivity extends AppCompatActivity {

    public static final String ANSWER_SHOWN = "com.example.aabrasha.firstandroidapp.answer_shown";

    private Toolbar toolbar;
    private TextView tvAnswer;
    private Button btnShowAnswer;
    private boolean answerWasShown = false;
    private boolean correctAnswer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            answerWasShown = savedInstanceState.getBoolean(ANSWER_SHOWN, false);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        correctAnswer = getIntent().getBooleanExtra(TrueFalseFragment.ANSWER_KEY, false);
        initComponents();
        setAnswerShownResult(answerWasShown);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ANSWER_SHOWN, answerWasShown);
    }

    private void initComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvAnswer = (TextView) findViewById(R.id.tv_answer);

        btnShowAnswer = (Button) findViewById(R.id.btn_show_answer);
        btnShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvAnswer.setText(String.valueOf(correctAnswer));
                setAnswerShownResult(true);
            }
        });

    }

    private void setAnswerShownResult(boolean shown) {
        Intent data = new Intent();
        answerWasShown = shown;
        data.putExtra(ANSWER_SHOWN, shown);
        setResult(RESULT_OK, data);
    }

}

package com.example.aabrasha.firstandroidapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aabrasha.firstandroidapp.R;
import com.example.aabrasha.firstandroidapp.model.TrueFalse;

public class TrueFalseActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btnTrue;
    Button btnFalse;
    Button btnNext;
    TextView tvQuestion;
    TextView tvCorrectAnswers;

    private int correctAnswers = 0;

    private TrueFalse[] questions = {
            new TrueFalse(R.string.question_oceans, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true)
    };

    private int currentQuestion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_false);
        setSupportActionBar(toolbar);

        initComponents();
        updateQuestion();

    }

    private void updateQuestion() {
        currentQuestion = ++currentQuestion % questions.length;
        int question = questions[currentQuestion].getQuestion();
        tvQuestion.setText(question);
    }

    private void checkUserAnswer(boolean userPressedTrue) {
        boolean correctAnswer = questions[currentQuestion].isTrueQuestion();
        if (correctAnswer == userPressedTrue) {
            correctAnswers++;
            tvCorrectAnswers.setText("Correct answers: " + correctAnswers);
            Toast.makeText(TrueFalseActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(TrueFalseActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
        updateQuestion();
    }

    private void initComponents() {

        toolbar = (Toolbar) findViewById(R.id.toolbar_true_false);
        tvQuestion = (TextView) findViewById(R.id.tv_question_text);
        tvCorrectAnswers = (TextView) findViewById(R.id.tv_correct_answers);
        btnTrue = (Button) findViewById(R.id.btnTrue);
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUserAnswer(true);
            }
        });
        btnFalse = (Button) findViewById(R.id.btnFalse);
        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUserAnswer(false);
                Toast.makeText(TrueFalseActivity.this, "False clicked", Toast.LENGTH_SHORT).show();
            }
        });
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateQuestion();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.menu_basic_item:
                Intent mainIntent = new Intent(this, MainActivity.class);
                startActivity(mainIntent);
                break;
            case R.id.menu_true_false__item:
                Intent trueFalseIntent = new Intent(this, TrueFalseActivity.class);
                startActivity(trueFalseIntent);
                break;
        }

        return true;
    }
}

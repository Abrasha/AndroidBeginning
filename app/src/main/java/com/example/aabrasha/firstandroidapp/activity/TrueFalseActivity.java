package com.example.aabrasha.firstandroidapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aabrasha.firstandroidapp.R;
import com.example.aabrasha.firstandroidapp.model.TrueFalse;

public class TrueFalseActivity extends AppCompatActivity {

    public static final String ANSWER_KEY = "com.example.aabrasha.firstandroidapp.answer";
    public static final String USER_CHEATED_KEY = "com.example.aabrasha.firstandroidapp.user_cheated";

    private static final int CHEAT_REQUEST_CODE = 0;

    private static final String TAG = "TrueFalse";
    private static final String SAVED_INDEX = "q_index";

    private Toolbar toolbar;
    private Button btnTrue;
    private Button btnFalse;
    private Button btnCheatAnswer;
    private ImageButton btnNext;
    private ImageButton btnPrevious;
    private TextView tvQuestion;
    private TextView tvCorrectAnswers;

    private boolean userCheatedOnCurrentQuestion = false;

    private int correctAnswers = 0;
    private int currentQuestion = 0;

    private TrueFalse[] questions = {
            new TrueFalse(R.string.question_oceans, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true)
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "Saved index: " + currentQuestion);
        outState.putInt(SAVED_INDEX, currentQuestion);
        outState.putBoolean(USER_CHEATED_KEY, userCheatedOnCurrentQuestion);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            currentQuestion = savedInstanceState.getInt(SAVED_INDEX, 0);
            userCheatedOnCurrentQuestion = savedInstanceState.getBoolean(USER_CHEATED_KEY, false);
            Log.d(TAG, "Loaded index: " + currentQuestion);
        }

        Log.d(TAG, "Starting creating activity");
        setContentView(R.layout.activity_true_false);

        initComponents();
        loadQuestion(currentQuestion);
        Log.d(TAG, "Finished creating activity");
    }

    private void loadQuestion(int index) {
        int question = questions[index].getQuestion();
        tvQuestion.setText(question);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "Starting starting activity");
        super.onStart();
        Log.d(TAG, "Finished starting activity");
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "Starting pausing activity");
        super.onPause();
        Log.d(TAG, "Finished pausing activity");
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "Starting destroying activity");
        super.onDestroy();
        Log.d(TAG, "Finishing destroying activity");
    }

    private void nextQuestion() {
        currentQuestion = ++currentQuestion % questions.length;
        int question = getCurrentQuestionId();
        tvQuestion.setText(question);
        userCheatedOnCurrentQuestion = false;
    }

    private void prevQuestion() {
        if (--currentQuestion < 0)
            currentQuestion = questions.length - 1;
        int question = getCurrentQuestionId();
        tvQuestion.setText(question);
        userCheatedOnCurrentQuestion = false;
    }

    private int getCurrentQuestionId() {
        return questions[currentQuestion].getQuestion();
    }

    private TrueFalse getCurrentQuestion() {
        return questions[currentQuestion];
    }

    private void checkUserAnswer(boolean userPressedTrue) {
        boolean correctAnswer = questions[currentQuestion].isTrueQuestion();
        if (correctAnswer == userPressedTrue) {
            correctAnswers++;
            if (userCheatedOnCurrentQuestion) {
                tvCorrectAnswers.setTextColor(Color.RED);
                tvCorrectAnswers.setText("You\'ve cheated! Correct answers: " + correctAnswers);
            } else {
                tvCorrectAnswers.setTextColor(Color.BLACK);
                tvCorrectAnswers.setText("Correct answers: " + correctAnswers);
            }
            Toast.makeText(TrueFalseActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(TrueFalseActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
        nextQuestion();
    }

    private void initComponents() {

        toolbar = (Toolbar) findViewById(R.id.toolbar_true_false);
        setSupportActionBar(toolbar);
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
            }
        });
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextQuestion();
            }
        });
        btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prevQuestion();
            }
        });

        btnCheatAnswer = (Button) findViewById(R.id.btn_cheat_answer);
        btnCheatAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cheatIntent = new Intent(TrueFalseActivity.this, CheatActivity.class);
                cheatIntent.putExtra(ANSWER_KEY, getCurrentQuestion().isTrueQuestion());
                startActivityForResult(cheatIntent, CHEAT_REQUEST_CODE);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;

        userCheatedOnCurrentQuestion = data.getBooleanExtra(CheatActivity.ANSWER_SHOWN, false);
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

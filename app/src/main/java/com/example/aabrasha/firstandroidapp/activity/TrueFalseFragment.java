package com.example.aabrasha.firstandroidapp.activity;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aabrasha.firstandroidapp.R;
import com.example.aabrasha.firstandroidapp.model.TrueFalse;

public class TrueFalseFragment extends Fragment {

    public static final String ANSWER_KEY = "com.example.aabrasha.firstandroidapp.answer";
    public static final String USER_CHEATED_KEY = "com.example.aabrasha.firstandroidapp.user_cheated";

    private static final int CHEAT_REQUEST_CODE = 0;

    private static final String TAG = "TrueFalse";
    private static final String SAVED_INDEX = "q_index";

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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "Saved index: " + currentQuestion);
        outState.putInt(SAVED_INDEX, currentQuestion);
        outState.putBoolean(USER_CHEATED_KEY, userCheatedOnCurrentQuestion);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            currentQuestion = savedInstanceState.getInt(SAVED_INDEX, 0);
            userCheatedOnCurrentQuestion = savedInstanceState.getBoolean(USER_CHEATED_KEY, false);
            Log.d(TAG, "Loaded index: " + currentQuestion);
        }

        Log.d(TAG, "Finished creating activity");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_true_false, container, false);

        initComponents(view);
        loadQuestion(currentQuestion);
        return view;
    }

    private void loadQuestion(int index) {
        int question = questions[index].getQuestion();
        tvQuestion.setText(question);
    }

    @Override
    public void onStart() {
        Log.d(TAG, "Starting starting activity");
        super.onStart();
        Log.d(TAG, "Finished starting activity");
    }

    @Override
    public void onPause() {
        Log.d(TAG, "Starting pausing activity");
        super.onPause();
        Log.d(TAG, "Finished pausing activity");
    }

    @Override
    public void onDestroy() {
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
            Toast.makeText(getActivity(), R.string.correct_toast, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
        nextQuestion();
    }

    private void initComponents(View view) {

        tvQuestion = (TextView) view.findViewById(R.id.tv_question_text);
        tvCorrectAnswers = (TextView) view.findViewById(R.id.tv_correct_answers);
        btnTrue = (Button) view.findViewById(R.id.btnTrue);
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUserAnswer(true);
            }
        });
        btnFalse = (Button) view.findViewById(R.id.btnFalse);
        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUserAnswer(false);
            }
        });
        btnNext = (ImageButton) view.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextQuestion();
            }
        });
        btnPrevious = (ImageButton) view.findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prevQuestion();
            }
        });

        btnCheatAnswer = (Button) view.findViewById(R.id.btn_cheat_answer);
        btnCheatAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cheatIntent = new Intent(getActivity(), CheatActivity.class);
                cheatIntent.putExtra(ANSWER_KEY, getCurrentQuestion().isTrueQuestion());
                startActivityForResult(cheatIntent, CHEAT_REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;

        userCheatedOnCurrentQuestion = data.getBooleanExtra(CheatActivity.ANSWER_SHOWN, false);
    }


}

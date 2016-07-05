package ua.aabrasha.edu.httppractice;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    static int value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    Log.d(TAG, "I am here " + value);
                    Toast.makeText(MainActivity.this, "I am here " + value, Toast.LENGTH_SHORT).show();
                    value++;
                }
                Log.d(TAG, "I am done " + value);
                Toast.makeText(MainActivity.this, "I am done ", Toast.LENGTH_SHORT).show();
            }
        });




        setContentView(R.layout.activity_main);

        FragmentManager fm = getFragmentManager();
        Fragment mainContainer = fm.findFragmentById(R.id.mainContainer);

        if (mainContainer == null) {
            mainContainer = new HttpFragment();
            fm.beginTransaction()
                    .add(R.id.mainContainer, mainContainer)
                    .commit();
        }

    }
}

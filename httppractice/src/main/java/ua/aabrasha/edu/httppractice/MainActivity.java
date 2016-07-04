package ua.aabrasha.edu.httppractice;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);




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

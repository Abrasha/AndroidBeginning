package ua.aabrasha.edu.crimeapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import ua.aabrasha.edu.crimeapp.fragments.ItemsFragment;

/**
 * Created by Andrii Abramov on 7/1/16.
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        FragmentManager fm = getSupportFragmentManager();

        Fragment listFragment = fm.findFragmentById(R.id.fragment_list);
        if (listFragment == null) {
            fm.beginTransaction()
                    .add(R.id.fragment_list, new ItemsFragment())
                    .commit();
        }


    }


}

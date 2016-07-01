package com.example.aabrasha.firstandroidapp.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.aabrasha.firstandroidapp.R;
import com.example.aabrasha.firstandroidapp.activity.crime.CrimeFragment;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("MainActivity Toolbar");
        setSupportActionBar(toolbar);

        fragmentManager = getFragmentManager();

        Fragment mainContentFragment = fragmentManager.findFragmentById(R.id.mainContentContainer);
        if (mainContentFragment == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.mainContentContainer, new CrimeFragment())
                    .commit();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();


        switch (itemId) {
            case R.id.menu_basic_item:

                fragmentManager.beginTransaction()
                        .replace(R.id.mainContentContainer, new HelloFragment())
                        .commit();
                break;
            case R.id.menu_true_false__item:
                fragmentManager.beginTransaction()
                        .replace(R.id.mainContentContainer, new TrueFalseFragment())
                        .commit();
                break;
            case R.id.menu_crime_fragment:
                fragmentManager.beginTransaction()
                        .replace(R.id.mainContentContainer, new CrimeFragment())
                        .commit();
                break;
        }

        return true;
    }
}

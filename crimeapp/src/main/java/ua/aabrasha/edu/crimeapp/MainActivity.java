package ua.aabrasha.edu.crimeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.crime_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.menu_item_take_picture:
                Intent intent = new Intent(this, CameraActivity.class);
                startActivity(intent);
            default:
                return false;
        }
    }

}

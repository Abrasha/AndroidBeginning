package ua.aabrasha.edu.crudapplication.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import ua.aabrasha.edu.crudapplication.R;
import ua.aabrasha.edu.crudapplication.fragment.CarListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FragmentManager fm = getFragmentManager();
        Fragment mainContainer = fm.findFragmentById(R.id.itemsFragment);
        if (mainContainer == null) {
            mainContainer = new CarListFragment();
            fm.beginTransaction()
                    .add(R.id.itemsFragment, mainContainer)
                    .commit();
        }

    }
}

package ua.aabrasha.edu.crimeapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import ua.aabrasha.edu.crimeapp.fragments.PreviewFragment;
import ua.aabrasha.edu.crimeapp.model.PeopleContainer;
import ua.aabrasha.edu.crimeapp.model.Person;

/**
 * Created by Andrii Abramov on 7/1/16.
 */
public class PagerActivity extends AppCompatActivity {

    private static final String TAG = PagerActivity.class.getSimpleName();

    List<Person> people;
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        people = PeopleContainer.getPeople();
        viewPager = new ViewPager(this);

        viewPager.setId(R.id.viewPagerId);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                PreviewFragment previewFragment = new PreviewFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(PreviewFragment.PERSON_ID_KEY, people.get(position).getId());
                previewFragment.setArguments(bundle);
                return previewFragment;
            }

            @Override
            public int getCount() {
                return people.size();
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, String.format("onPageScrolled, pos=%d, posOffset=%f, posOffPixel=%d", position, positionOffset, positionOffsetPixels));
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, String.format("onPageScrolled, pos=%d", position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, String.format("onPageScrollStateChanged, pos=%d", state));
            }
        });

        setContentView(viewPager);

    }
}

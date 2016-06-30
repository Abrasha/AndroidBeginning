package com.example.aabrasha.firstandroidapp.activity.crime;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aabrasha.firstandroidapp.R;

public class CrimeFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_crime, container, false);

        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.crimeFragmentContainer);

        if (fragment == null) {
            fragment = new CrimeFragment();
            fm.beginTransaction()
                    .add(R.id.crimeFragmentContainer, fragment)
                    .commit();
        }
        return view;
    }
}

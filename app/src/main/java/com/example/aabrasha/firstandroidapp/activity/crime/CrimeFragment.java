package com.example.aabrasha.firstandroidapp.activity.crime;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.aabrasha.firstandroidapp.R;
import com.example.aabrasha.firstandroidapp.activity.crime.helper.CrimeHolder;
import com.example.aabrasha.firstandroidapp.model.Crime;

import java.util.List;

public class CrimeFragment extends ListFragment {

    private EditText etDescription;
    private Button btnDate;
    private CheckBox cbSolved;

    private List<Crime> crimes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        crimes = CrimeHolder.getInstance(getActivity().getApplicationContext()).getCrimes();

        View view = inflater.inflate(R.layout.content_crime, container, false);

//        btnDate = (Button) view.findViewById(R.id.crimeDate);
//        etDescription = (EditText) view.findViewById(R.id.crimeDescription);
//        cbSolved = (CheckBox) view.findViewById(R.id.crimeSolved);

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

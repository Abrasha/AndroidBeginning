package com.example.aabrasha.firstandroidapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.aabrasha.firstandroidapp.R;
import com.example.aabrasha.firstandroidapp.model.Crime;

/**
 * Created by Andrii Abramov on 6/30/16.
 */
public class CrimeFragment extends Fragment {

    private Crime crime;
    private EditText etCrimeTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        crime = new Crime();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.crime_fragment, container, false);

        etCrimeTitle = (EditText) view.findViewById(R.id.crime_title);
        etCrimeTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                crime.setTitle(charSequence.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }
}

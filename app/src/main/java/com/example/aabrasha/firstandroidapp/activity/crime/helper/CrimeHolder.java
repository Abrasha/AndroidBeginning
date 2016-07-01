package com.example.aabrasha.firstandroidapp.activity.crime.helper;

import android.content.Context;

import com.example.aabrasha.firstandroidapp.model.Crime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrii Abramov on 7/1/16.
 */
public class CrimeHolder {

    private static CrimeHolder INSTANCE;

    private List<Crime> crimes;
    private Context appContext;

    private CrimeHolder(Context appContext) {
        crimes = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime # " + i);
            crime.setSolved(i % 3 == 0);
            crimes.add(crime);
        }
        this.appContext = appContext;
    }

    public boolean addCrime(Crime crime) {
        return crimes.add(crime);
    }

    public Crime getCrime(int UUI) {
        for (Crime crime : crimes) {
            if (crime.getId().equals(UUI))
                return crime;
        }
        return null;
    }

    public List<Crime> getCrimes() {
        return crimes;
    }

    public static CrimeHolder getInstance(Context appContext) {
        if (INSTANCE == null)
            INSTANCE = new CrimeHolder(appContext);

        return INSTANCE;
    }

}

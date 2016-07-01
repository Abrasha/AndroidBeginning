package ua.aabrasha.edu.crimeapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.UUID;

import ua.aabrasha.edu.crimeapp.R;
import ua.aabrasha.edu.crimeapp.model.PeopleContainer;
import ua.aabrasha.edu.crimeapp.model.Person;

/**
 * Created by Andrii Abramov on 7/1/16.
 */
public class PreviewFragment extends android.support.v4.app.Fragment {

    public static final String PERSON_ID_KEY = "edu.aabrasha.crimeapp.person_key";

    private Person currentPerson;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            UUID personId = (UUID) bundle.getSerializable(PERSON_ID_KEY);
            currentPerson = PeopleContainer.getWithUUID(personId);
        } else {
            currentPerson = new Person();
        }
        View v = inflater.inflate(R.layout.item_preview, container, false);

        TextView tvPersonName = (TextView) v.findViewById(R.id.tvPreviewPersonName);
        tvPersonName.setText(currentPerson.getName());

        TextView tvPreviewPersonAge = (TextView) v.findViewById(R.id.tvPreviewPersonAge);
        tvPreviewPersonAge.setText(String.valueOf(currentPerson.getAge()));

        TextView tvPreviewPersonId = (TextView) v.findViewById(R.id.tvPreviewPersonId);
        tvPreviewPersonId.setText(currentPerson.getId().toString());

        TextView tvPreviewPersonGender = (TextView) v.findViewById(R.id.tvPreviewPersonGender);
        tvPreviewPersonGender.setText(currentPerson.getGender().toString());

        return v;
    }


}

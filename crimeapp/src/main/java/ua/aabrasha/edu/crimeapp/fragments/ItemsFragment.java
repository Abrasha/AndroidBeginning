package ua.aabrasha.edu.crimeapp.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ua.aabrasha.edu.crimeapp.R;
import ua.aabrasha.edu.crimeapp.model.PeopleContainer;
import ua.aabrasha.edu.crimeapp.model.Person;

/**
 * Created by Andrii Abramov on 7/1/16.
 */
public class ItemsFragment extends android.support.v4.app.ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new PersonListItemAdapter(PeopleContainer.getPeople()));
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        FragmentManager fm = getActivity().getSupportFragmentManager();

        PreviewFragment previewFragment = new PreviewFragment();

        Bundle args = new Bundle();
        args.putSerializable(PreviewFragment.PERSON_ID_KEY, ((Person) getListAdapter().getItem(position)).getId());

        previewFragment.setArguments(args);
        fm.beginTransaction()
                .replace(R.id.fragment_preview, previewFragment)
                .commit();


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private class PersonListItemAdapter extends ArrayAdapter<Person> {

        public PersonListItemAdapter(List<Person> people) {
            super(getActivity(), R.layout.item_list_item, people);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.item_list_item, null);
            }

            Person p = getItem(position);
            TextView tvPersonGender = (TextView) convertView.findViewById(R.id.tvPersonGender);

            TextView tvPersonName = (TextView) convertView.findViewById(R.id.tvPersonName);
            TextView tvPersonAge = (TextView) convertView.findViewById(R.id.tvPersonAge);
            TextView tvPersonId = (TextView) convertView.findViewById(R.id.tvPersonId);

            tvPersonAge.setText(String.valueOf(p.getAge()));
            tvPersonName.setText(p.getName());
            tvPersonId.setText(p.getId().toString());
            tvPersonGender.setText(p.getGender().toString());

            return convertView;
        }
    }

}

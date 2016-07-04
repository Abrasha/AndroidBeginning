package ua.aabrasha.edu.crimeapp.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            registerForContextMenu(getListView());
        } else {
            getListView().setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        }

        getListView().setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater = actionMode.getMenuInflater();
                inflater.inflate(R.menu.crime_list_item_context, menu);
                actionMode.setTitle("Check people...");
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_item_delete_crime:
                        PersonListItemAdapter personAdapter = (PersonListItemAdapter) getListAdapter();
                        for (int i = 0; i < personAdapter.getCount(); i++) {
                            if (getListView().isItemChecked(i)) {
                                PeopleContainer.remove(personAdapter.getItem(i));
                            }
                        }
                        actionMode.finish();
                        personAdapter.notifyDataSetChanged();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });

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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_item_delete_crime:
                handleRemoveContextClick(item);
                break;

            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    private void handleRemoveContextClick(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        PersonListItemAdapter personAdapter = (PersonListItemAdapter) getListAdapter();
        Person personToRemove = personAdapter.getItem(position);
        PeopleContainer.remove(personToRemove);
        personAdapter.notifyDataSetChanged();
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

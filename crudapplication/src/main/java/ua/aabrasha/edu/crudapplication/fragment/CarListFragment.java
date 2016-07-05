package ua.aabrasha.edu.crudapplication.fragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import ua.aabrasha.edu.crudapplication.R;
import ua.aabrasha.edu.crudapplication.database.CarDatabaseHelper;
import ua.aabrasha.edu.crudapplication.model.Car;
import ua.aabrasha.edu.crudapplication.model.CarManager;

import java.util.List;

import static ua.aabrasha.edu.crudapplication.database.CarDatabaseHelper.CAR_ID_COLUMN_NAME;
import static ua.aabrasha.edu.crudapplication.database.CarDatabaseHelper.CAR_TABLE_NAME;

/**
 * Created by Andrii Abramov on 7/5/16.
 */
public class CarListFragment extends ListFragment {

    CarListAdapter adapter;
    CarDatabaseHelper carDB = new CarDatabaseHelper(getActivity());


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new CarListAdapter(CarManager.getCarManager(getActivity()).getCars());
        setListAdapter(adapter);
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.cars_list_view, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        getListView().setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                actionMode.getMenuInflater().inflate(R.menu.cars_menu_context_items, menu);
                actionMode.setTitle("Check cars ...");
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.menu_item_delete_car:
                        CarListAdapter adapter = (CarListAdapter) getListAdapter();
                        for (int i = 0; i < adapter.getCount(); i++) {
                            if (getListView().isItemChecked(i)) {
                                final Car checkedCar = adapter.getItem(i);
                                CarManager.getCarManager(getActivity()).remove(checkedCar);
                                final int deleted = carDB.getWritableDatabase().delete(CAR_TABLE_NAME, CAR_ID_COLUMN_NAME + "=" + checkedCar.getId(), null);
                                Log.d(getClass().getSimpleName(), "deleted rows: " + deleted);
                            }
                        }
                        actionMode.finish();
                        adapter.notifyDataSetChanged();
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

    private class CarListAdapter extends ArrayAdapter<Car> {

        public CarListAdapter(List<Car> objects) {
            super(getActivity(), 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_car, null);
            }
            TextView tvCarName = (TextView) convertView.findViewById(R.id.tvCarName);
            TextView tvCarYear = (TextView) convertView.findViewById(R.id.tvCarYear);
            TextView tvCarId = (TextView) convertView.findViewById(R.id.tvCarId);
            Car car = getItem(position);
            tvCarName.setText(car.getName());
            tvCarId.setText(String.valueOf(car.getId()));
            tvCarYear.setText(String.valueOf(car.getYear()));
            return convertView;
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        final CarListAdapter adapter = (CarListAdapter) getListAdapter();
        final Car selectedItem = adapter.getItem(position);
        final CarPreviewFragment fragment = CarPreviewFragment.newInstance(selectedItem.getId(), adapter);
        getFragmentManager().beginTransaction()
                .replace(R.id.previewFragment, fragment)
                .commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.cars_menu_items, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_item_new_car:
                final CarListAdapter adapter = (CarListAdapter) getListAdapter();
                final CarPreviewFragment fragment = new CarPreviewFragment();
                fragment.setAdapter(adapter);
                getFragmentManager().beginTransaction()
                        .replace(R.id.previewFragment, fragment)
                        .commit();
                break;
        }

        return true;
    }
}

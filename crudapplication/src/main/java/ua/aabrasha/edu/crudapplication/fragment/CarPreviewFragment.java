package ua.aabrasha.edu.crudapplication.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import ua.aabrasha.edu.crudapplication.R;
import ua.aabrasha.edu.crudapplication.database.CarCursorAdapter;
import ua.aabrasha.edu.crudapplication.model.Car;
import ua.aabrasha.edu.crudapplication.model.CarManager;

/**
 * Created by Andrii Abramov on 7/5/16.
 */
public class CarPreviewFragment extends Fragment {

    private static final String KEY_CAR_ID = "key.car.id";

    // nvm
    private CarCursorAdapter adapter;

    private CarManager carManager;


    private Car currentCar = null;
    private boolean isAddingNew = false;

    public void setAdapter(CarCursorAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carManager = CarManager.getCarManager(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle args = getArguments();

        if (args != null) {
            final long carId = args.getLong(KEY_CAR_ID);
            currentCar = carManager.getById(carId);
        } else {
            isAddingNew = true;
            currentCar = new Car(0, "Car #", (int) (Math.random() * 200));
        }


        final View previewView = inflater.inflate(R.layout.car_preview, container, false);
        TextView tvCarTitle = (TextView) previewView.findViewById(R.id.tvCarTitle);
        EditText etCarName = (EditText) previewView.findViewById(R.id.etCarName);
        EditText etCarYear = (EditText) previewView.findViewById(R.id.etCarYear);
        final Button btnSaveNewCar = (Button) previewView.findViewById(R.id.btnSaveNewCar);

        if (isAddingNew) {
            btnSaveNewCar.setVisibility(View.VISIBLE);
            btnSaveNewCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    carManager.addNewCar(currentCar);
                    adapter = new CarCursorAdapter(getActivity(), carManager.getCarsCursor());
                    setAdapter(adapter);
                    view.setVisibility(View.GONE);
                }
            });
        }


        tvCarTitle.setText("Preview of " + currentCar.getName());

        etCarName.setText(currentCar.getName());
        etCarName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                currentCar.setName(charSequence.toString());
                if (adapter != null)
                    adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etCarYear.setText(String.valueOf(currentCar.getYear()));
        etCarYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                currentCar.setYear(Integer.valueOf(charSequence.toString()));
                if (adapter != null)
                    adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return previewView;
    }

    public static CarPreviewFragment newInstance(long carId, CarCursorAdapter adapter) {
        Bundle args = new Bundle();
        args.putLong(KEY_CAR_ID, carId);
        CarPreviewFragment result = new CarPreviewFragment();
        result.setArguments(args);
        result.setAdapter(adapter);
        return result;
    }

}

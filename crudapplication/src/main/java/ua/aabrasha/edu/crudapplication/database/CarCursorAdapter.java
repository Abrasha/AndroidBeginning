package ua.aabrasha.edu.crudapplication.database;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import ua.aabrasha.edu.crudapplication.R;
import ua.aabrasha.edu.crudapplication.model.Car;

/**
 * Created by Andrii Abramov on 7/7/16.
 */
public class CarCursorAdapter extends CursorAdapter {

    private CarCursor carCursor;

    public CarCursorAdapter(Context context, CarCursor carCursor) {
        super(context, carCursor, 0);
        this.carCursor = carCursor;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View result = LayoutInflater.from(context).inflate(R.layout.list_item_car, null);

        return result;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        Car car = carCursor.getCar();

        TextView tvCarName = (TextView) view.findViewById(R.id.tvCarName);
        TextView tvCarYear = (TextView) view.findViewById(R.id.tvCarYear);
        TextView tvCarId = (TextView) view.findViewById(R.id.tvCarId);
        tvCarName.setText(car.getName());
        tvCarId.setText(String.valueOf(car.getId()));
        tvCarYear.setText(String.valueOf(car.getYear()));
    }
}

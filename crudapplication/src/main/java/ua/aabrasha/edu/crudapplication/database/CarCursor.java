package ua.aabrasha.edu.crudapplication.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import ua.aabrasha.edu.crudapplication.model.Car;

/**
 * Created by Andrii Abramov on 7/7/16.
 */
public class CarCursor extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CarCursor(Cursor cursor) {
        super(cursor);
    }

    public Car getCar() {
        if (isBeforeFirst() || isAfterLast())
            return null;
        Car result = new Car();
        result.setId(getInt(0)); // nvm
        result.setName(getString(1));
        result.setYear(getInt(2));
        return result;
    }

}

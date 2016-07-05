package ua.aabrasha.edu.crudapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import ua.aabrasha.edu.crudapplication.model.Car;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrii Abramov on 7/5/16.
 */
public class CarDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "car.sqlite";
    private static final int VERSION = 2;

    public static final String CAR_TABLE_NAME = "my_cars";
    public static final String CAR_ID_COLUMN_NAME = "id";
    public static final String CAR_NAME_COLUMN_NAME = "name";
    public static final String CAR_YEAR_COLUMN_NAME = "year";

    public CarDatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + CAR_TABLE_NAME +
                " ( id integer primary key autoincrement," +
                " name varchar(60) NOT NULL," +
                " year integer NOT NULL" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + CAR_TABLE_NAME;
        db.execSQL(sql);

        onCreate(db);
    }

    public long insertCar(Car car) {
        ContentValues cv = new ContentValues();
        cv.put(CAR_NAME_COLUMN_NAME, car.getName());
        cv.put(CAR_YEAR_COLUMN_NAME, car.getYear());
        final long id = getWritableDatabase().insert(CAR_TABLE_NAME, null, cv);
        car.setId(id); // nvm
        return id;
    }

    public List<Car> getAllCars() {
        List<Car> result = new ArrayList<>();
        Cursor c = getReadableDatabase().query(CAR_TABLE_NAME, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            result.add(parseCursor(c));
            while (c.moveToNext()) {
                result.add(parseCursor(c));
                System.out.println(c.getString(1));
            }
        }
        return result;
    }

    private Car parseCursor(Cursor c) {
        Car result = new Car();
        result.setName(c.getString(1));
        result.setId(c.getInt(0));
        result.setYear(c.getInt(2));
        return result;
    }

}

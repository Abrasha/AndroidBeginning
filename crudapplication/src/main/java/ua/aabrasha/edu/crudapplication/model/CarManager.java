package ua.aabrasha.edu.crudapplication.model;

import android.content.Context;
import ua.aabrasha.edu.crudapplication.database.CarDatabaseHelper;

import java.util.List;

/**
 * Created by Andrii Abramov on 7/5/16.
 */
public class CarManager {

    private List<Car> cars;
    private CarDatabaseHelper carDB;

    private static CarManager CAR_MANAGER;


    public static CarManager getCarManager(Context context) {
        if (CAR_MANAGER == null) {
            CAR_MANAGER = new CarManager(context);
        }
        return CAR_MANAGER;
    }

    public List<Car> getCars() {
        return cars;
    }

    public Car getCar(int index) {
        return cars.get(index);
    }

    private CarManager(Context context) {
        carDB = new CarDatabaseHelper(context);
        cars = carDB.getAllCars();
    }

    public Car getById(long id) {
        for (Car car : cars) {
            if (car.getId() == id)
                return car;
        }
        return null;
    }

    public int getNextId() {
        return cars.size();
    }

    public boolean addNewCar(Car car) {
        carDB.insertCar(car);
        return cars.add(car);
    }

    public void remove(Car item) {
        cars.remove(item);
    }
}

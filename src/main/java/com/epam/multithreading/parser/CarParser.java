package com.epam.multithreading.parser;

import com.epam.multithreading.model.entity.Car;
import com.epam.multithreading.model.entity.CarType;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CarParser {
    private final static String CARS_DELIMITER = ";";

    public List<Car> parseCar(String line) {
        List<Car> result = new ArrayList<>();
        String[] cars = line.split(CARS_DELIMITER);
        for (String car : cars) {
            result.add(new Car(CarType.valueOf(car.toUpperCase(Locale.ROOT).trim())));
        }
        return result;
    }
}

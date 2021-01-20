package com.epam.multithreading.model.initializer;

import com.epam.multithreading.model.entity.Car;
import com.epam.multithreading.model.entity.RiverFerry;
import com.epam.multithreading.parser.CarParser;

import java.util.ArrayList;
import java.util.List;

public class Initializer {
    public void initializeCars(List<String> lines) {
        CarParser parser = new CarParser();
        List<Car> cars = new ArrayList<>();
        for (String line : lines) {
            cars.addAll(parser.parseCar(line));
        }
        RiverFerry.setCarsAmount(cars.size());
        startThreads(cars);
    }

    private <T extends Thread> void startThreads(List<T> threads) {
        for (T thread : threads) {
            thread.start();
        }
    }
}

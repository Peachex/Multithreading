package com.epam.multithreading.main;

import com.epam.multithreading.exception.ReaderException;
import com.epam.multithreading.model.initializer.Initializer;
import com.epam.multithreading.reader.CarReader;
import java.util.List;

public class ProjectMain {
    public static void main(String[] args) throws ReaderException {
        CarReader reader = new CarReader();
        List<String> lines = reader.readDataFromFile("data\\cars.txt");
        Initializer carInitializer = new Initializer();
        carInitializer.initializeCars(lines);
    }
}

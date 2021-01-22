package com.epam.multithreading.main;

import com.epam.multithreading.exception.ReaderException;
import com.epam.multithreading.model.initializer.Initializer;
import com.epam.multithreading.reader.CarReader;

import java.util.List;

public class ProjectMain {
    public static void main(String[] args) throws ReaderException {
        CarReader reader = new CarReader();
        String path = "data\\cars.txt";
        List<String> lines = reader.readDataFromFile(path);
        Initializer carInitializer = new Initializer();
        carInitializer.initializeCars(lines);
    }
}

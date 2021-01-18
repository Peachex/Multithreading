package com.epam.multithreading.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RiverFerry {
    private static final RiverFerry ferry = new RiverFerry();
    private static final int FERRY_WEIGHT_CAPACITY = 15;
    private static final int FERRY_AREA_CAPACITY = 12;
    private static int CURRENT_WEIGHT;
    private static int CURRENT_AREA;

    static {
        CURRENT_WEIGHT = 0;
        CURRENT_AREA = 0;
    }

    private Lock locker;
    private List<Car> carsOnFerry;;

    private RiverFerry() {
        locker = new ReentrantLock(true);
        carsOnFerry = new ArrayList<>();
    }

    public static RiverFerry getFerry() {
        return ferry;
    }

    public static int getCurrentWeight() {
        return CURRENT_WEIGHT;
    }

    public static void setCurrentWeight(int currentWeight) {
        CURRENT_WEIGHT = currentWeight;
    }

    public static int getCurrentArea() {
        return CURRENT_AREA;
    }

    public static void setCurrentArea(int currentArea) {
        CURRENT_AREA = currentArea;
    }

    public boolean isAvailable(Car car) {
        CURRENT_WEIGHT += car.getType().getWeight();
        int freeWeight = FERRY_WEIGHT_CAPACITY - CURRENT_WEIGHT;
        CURRENT_AREA += car.getType().getArea();
        int freeArea = FERRY_AREA_CAPACITY - CURRENT_AREA;
        return (freeWeight > 0 && freeArea > 0);
    }


    public  void add(Car car) {

    }

    public void startFerrying() {

    }
}

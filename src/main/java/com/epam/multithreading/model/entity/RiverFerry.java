package com.epam.multithreading.model.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RiverFerry {
    private static final Logger logger = LogManager.getLogger();
    private static final int FERRY_WEIGHT_CAPACITY = 15;
    private static final int FERRY_AREA_CAPACITY = 12;
    private static final long TIME_TO_WAIT = 50;
    private static final long TIME_TO_WAIT_BETWEEN_FUNCTIONS = 2;
    private static final Lock locker;
    private static Condition condition;
    private static RiverFerry ferry;
    private static AtomicInteger currentWeight;
    private static AtomicInteger currentArea;
    private static AtomicInteger carsAmount;
    private static AtomicBoolean isFerryCreated;

    static {
        locker = new ReentrantLock(true);
        condition = locker.newCondition();
        currentWeight = new AtomicInteger();
        currentArea = new AtomicInteger();
        carsAmount = new AtomicInteger();
        isFerryCreated = new AtomicBoolean();
    }


    private List<Car> carsOnFerry;

    private RiverFerry() {
        carsOnFerry = new ArrayList<>();
    }

    public static RiverFerry getFerry() {
        if (!isFerryCreated.get()) {
            try {
                locker.lock();
                if (ferry == null) {
                    ferry = new RiverFerry();
                    isFerryCreated.set(true);
                }
            } finally {
                locker.unlock();
            }
        }
        return ferry;
    }

    public static long getTimeToWait() {
        return TIME_TO_WAIT;
    }

    public static long getTimeToWaitBetweenFunctions() {
        return TIME_TO_WAIT_BETWEEN_FUNCTIONS;
    }

    public static int getFerryWeightCapacity() {
        return FERRY_WEIGHT_CAPACITY;
    }

    public static int getFerryAreaCapacity() {
        return FERRY_AREA_CAPACITY;
    }

    public static AtomicBoolean getIsFerryCreated() {
        return isFerryCreated;
    }

    public static AtomicInteger getCurrentWeight() {
        return currentWeight;
    }

    public static void setCurrentWeight(int currentWeight) {
        RiverFerry.currentWeight.set(currentWeight);
    }

    public static AtomicInteger getCurrentArea() {
        return currentArea;
    }

    public static void setCurrentArea(int currentArea) {
        RiverFerry.currentArea.set(currentArea);
    }

    public static AtomicInteger getCarsAmount() {
        return carsAmount;
    }

    public static void setCarsAmount(int carsAmount) {
        RiverFerry.carsAmount.set(carsAmount);
    }

    public boolean checkFreeSpace(Car car) {
        int totalWeight = currentWeight.get() + car.getType().getWeight();
        int totalArea = currentArea.get() + car.getType().getArea();
        return (totalWeight <= FERRY_WEIGHT_CAPACITY && totalArea <= FERRY_AREA_CAPACITY);
    }

    public void addToFerry(Car car) {
        try {
            locker.lock();
            if (!checkFreeSpace(car)) {
                if (car.getType().equals(CarType.TRUCK)) {
                    condition.await(TIME_TO_WAIT, TimeUnit.MILLISECONDS);
                    condition.signalAll();
                }
            }
            if (!checkFreeSpace(car)) {
                TimeUnit.SECONDS.sleep(TIME_TO_WAIT_BETWEEN_FUNCTIONS);
                startFerrying();
            }
            this.carsOnFerry.add(car);
            currentWeight.set(currentWeight.get() + car.getType().getWeight());
            currentArea.set(currentArea.get() + car.getType().getArea());
            carsAmount.decrementAndGet();
            logger.log(Level.DEBUG, "id = " + car.getId() + " (" + car.getType() + ")" + " - added to the ferry");
            if (carsAmount.get() == 0) {
                TimeUnit.SECONDS.sleep(TIME_TO_WAIT_BETWEEN_FUNCTIONS);
                startFerrying();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            locker.unlock();
        }
    }

    public void startFerrying() {
        logger.log(Level.DEBUG, "Current weight = " + currentWeight + " (<=" + FERRY_WEIGHT_CAPACITY + ")");
        logger.log(Level.DEBUG, "Current area = " + currentArea + " (<=" + FERRY_AREA_CAPACITY + ")");
        int i = 0;
        while (this.carsOnFerry.size() > 0) {
            logger.log(Level.DEBUG, "id = " + carsOnFerry.get(i).getId() + " (" + carsOnFerry.get(i).getType() + ")" + " - complete ferrying");
            currentWeight.set(currentWeight.get() - carsOnFerry.get(i).getType().getWeight());
            currentArea.set(currentArea.get() - carsOnFerry.get(i).getType().getArea());
            carsOnFerry.remove(carsOnFerry.get(i));
        }
        try {
            TimeUnit.SECONDS.sleep(TIME_TO_WAIT_BETWEEN_FUNCTIONS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

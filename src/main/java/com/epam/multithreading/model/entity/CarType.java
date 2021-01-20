package com.epam.multithreading.model.entity;

public enum CarType {
    TRUCK(5, 4), PASSENGER(3, 2);
    private final int weight;
    private final int area;

    CarType(int weight, int area) {
        this.weight = weight;
        this.area = area;
    }

    public int getWeight() {
        return weight;
    }

    public int getArea() {
        return area;
    }
}

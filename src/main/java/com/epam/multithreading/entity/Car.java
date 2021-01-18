package com.epam.multithreading.entity;

import com.epam.multithreading.util.IdGenerator;

public class Car extends Thread {
    private static final int TRUCK_WEIGHT = 5;
    private static final int TRUCK_AREA = 4;
    private static final int PASSENGER_WEIGHT = 3;
    private static final int PASSENGER_AREA = 2;
    private final long id;
    private RiverFerry ferry = RiverFerry.getFerry();
    private Type type;

    public Car(Type type) {
        this.type = type;
        this.id = IdGenerator.getIncreasedId();
    }

    @Override
    public void run() {
        ferry.add(this);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Car that = (Car) o;
        return this.type == that.type;
    }

    @Override
    public int hashCode() {
        int result = 31 + this.type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id = ")
                .append(this.id)
                .append(", ")
                .append(this.type)
                .append(": weight = ")
                .append(this.type.weight)
                .append(", area = ")
                .append(this.type.area);
        return sb.toString();
    }

    public enum Type {
        TRUCK(TRUCK_WEIGHT, TRUCK_AREA), PASSENGER(PASSENGER_WEIGHT, PASSENGER_AREA);
        private final int weight;
        private final int area;

        Type(int weight, int area) {
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
}

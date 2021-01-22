package com.epam.multithreading.model.entity;

import com.epam.multithreading.util.IdGenerator;

public class Car extends Thread {
    private final long id;
    private CarType type;

    public Car(CarType type) {
        this.type = type;
        this.id = IdGenerator.getIncreasedId();
    }

    @Override
    public void run() {
        RiverFerry.getFerry().addToFerry(this);
    }

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
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
        sb.append("id = ");
        sb.append(this.id);
        sb.append(", ");
        sb.append(this.type);
        sb.append(": weight = ");
        sb.append(this.type.getWeight());
        sb.append(", area = ");
        sb.append(this.type.getArea());
        return sb.toString();
    }
}

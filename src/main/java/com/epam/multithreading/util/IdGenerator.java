package com.epam.multithreading.util;

public class IdGenerator {
    private static long currentId = 1;

    public static long getIncreasedId() {
        return currentId++;
    }
}

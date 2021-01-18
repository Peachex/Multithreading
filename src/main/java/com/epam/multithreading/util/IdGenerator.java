package com.epam.multithreading.util;

public class IdGenerator {
    private static long currentId = 0;

    public static long getIncreasedId() {
        return currentId++;
    }
}

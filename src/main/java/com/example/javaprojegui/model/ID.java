package com.example.javaprojegui.model;
import java.util.Random;

public class ID {
    private static final Random random = new Random();

    public static int randomId() {
        return random.nextInt(1000001) + 1;
    }
}

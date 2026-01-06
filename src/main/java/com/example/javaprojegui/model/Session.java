package com.example.javaprojegui.model;

public class Session {

    public static String currentUser;
    public static String currentEmail;
    public static String currentRole;

    public static void clear() {
        currentUser = null;
        currentEmail = null;
        currentRole = null;
    }
}

package com.example.javaprojegui.util;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class AuthCSVUtil {

    private static final String USER_FILE =
            "src/main/java/com/example/javaprojegui/database/kullanici.csv";

    private static final String ADMIN_FILE =
            "src/main/java/com/example/javaprojegui/database/admin.csv";

    // =========================
    // KAYIT
    // =========================
    public static void saveUser(
            String username,
            String email,
            String password,
            String role
    ) throws IOException {

        String path = role.equalsIgnoreCase("ADMIN")
                ? ADMIN_FILE
                : USER_FILE;

        File file = new File(path);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try (FileWriter fw = new FileWriter(file)) {
                fw.write("username,email,password");
            }
        }

        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(System.lineSeparator());
            fw.write(username + "," + email + "," + password);
        }
    }

    // =========================
    // ORTAK OKUMA
    // =========================
    private static List<String[]> read(String path) throws IOException {
        List<String[]> list = new ArrayList<>();

        if (!Files.exists(Paths.get(path))) {
            return list;
        }

        List<String> lines = Files.readAllLines(Paths.get(path));
        for (int i = 1; i < lines.size(); i++) {
            list.add(lines.get(i).split(","));
        }
        return list;
    }

    // =========================
    // GİRİŞ
    // =========================
    public static String authenticate(String username, String password)
            throws IOException {

        if (check(username, password, ADMIN_FILE)) {
            return "ADMIN";
        }

        if (check(username, password, USER_FILE)) {
            return "KULLANICI";
        }

        return null;
    }

    private static boolean check(
            String username,
            String password,
            String path
    ) throws IOException {

        for (String[] u : read(path)) {
            if (u[0].equals(username) && u[2].equals(password)) {
                return true;
            }
        }
        return false;
    }

    // =========================
    // VAR MI KONTROLLERİ
    // =========================
    public static boolean usernameExists(String username) throws IOException {
        return exists(username, USER_FILE) || exists(username, ADMIN_FILE);
    }

    public static boolean emailExists(String email) throws IOException {
        return emailExistsIn(email, USER_FILE) || emailExistsIn(email, ADMIN_FILE);
    }

    private static boolean exists(String username, String path)
            throws IOException {

        for (String[] u : read(path)) {
            if (u[0].equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    private static boolean emailExistsIn(String email, String path)
            throws IOException {

        for (String[] u : read(path)) {
            if (u[1].equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    // =========================
    // ŞİFRE SIFIRLAMA
    // =========================
    public static boolean resetPassword(
            String username,
            String email,
            String newPassword
    ) throws IOException {

        return resetInFile(username, email, newPassword, USER_FILE)
                || resetInFile(username, email, newPassword, ADMIN_FILE);
    }

    private static boolean resetInFile(
            String username,
            String email,
            String newPassword,
            String path
    ) throws IOException {

        if (!Files.exists(Paths.get(path))) return false;

        List<String> lines = Files.readAllLines(Paths.get(path));
        List<String> updated = new ArrayList<>();

        boolean found = false;
        updated.add(lines.get(0)); // header

        for (int i = 1; i < lines.size(); i++) {
            String[] u = lines.get(i).split(",");

            if (u[0].equals(username) && u[1].equals(email)) {
                u[2] = newPassword;
                found = true;
            }
            updated.add(String.join(",", u));
        }

        if (found) {
            Files.write(Paths.get(path), updated);
        }

        return found;
    }

    // =========================
    // ŞİFRE DEĞİŞTİRME
    // =========================
    public static boolean changePassword(
            String username,
            String oldPassword,
            String newPassword
    ) throws IOException {

        return changeInFile(username, oldPassword, newPassword, USER_FILE)
                || changeInFile(username, oldPassword, newPassword, ADMIN_FILE);
    }

    private static boolean changeInFile(
            String username,
            String oldPassword,
            String newPassword,
            String path
    ) throws IOException {

        if (!Files.exists(Paths.get(path))) return false;

        List<String> lines = Files.readAllLines(Paths.get(path));
        List<String> updated = new ArrayList<>();

        boolean changed = false;
        updated.add(lines.get(0)); // header

        for (int i = 1; i < lines.size(); i++) {
            String[] u = lines.get(i).split(",");

            if (u[0].equals(username)) {
                if (!u[2].equals(oldPassword)) {
                    return false;
                }
                u[2] = newPassword;
                changed = true;
            }
            updated.add(String.join(",", u));
        }

        if (changed) {
            Files.write(Paths.get(path), updated);
        }

        return changed;
    }
}

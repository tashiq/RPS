package com.example.rps;

public class User {
    private static int id;
    private static String name;
    private static String username;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        User.id = id;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        User.name = name;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        User.username = username;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        User.address = address;
    }

    private static String address;
}

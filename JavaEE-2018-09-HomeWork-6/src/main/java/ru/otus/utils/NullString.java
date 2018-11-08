package ru.otus.utils;

public class NullString
{
    public static Long returnLong(String s) {
        return "NULL".equals(s.toUpperCase()) ? null : Long.parseLong(s);
    }

    public static String returnString(String s) {
        return "NULL".equals(s.toUpperCase()) ? null : s;
    }
}

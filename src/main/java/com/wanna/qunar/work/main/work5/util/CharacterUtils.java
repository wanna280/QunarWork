package com.wanna.qunar.work.main.work5.util;

public class CharacterUtils {
    public static boolean isNumber(char ch) {
        return ch >= '0' && ch <= '9';
    }

    public static boolean isLetter(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
    }

    public static boolean isChinese(char ch) {
        return String.valueOf(ch).matches("[\\u4e00-\\u9fa5]");
    }
}

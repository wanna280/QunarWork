package com.wanna.qunar.work.core.util;

public abstract class AssertUtils {
    public static void assertState(boolean state, String message) {
        if (!state) {
            throw new IllegalStateException(message);
        }
    }
}

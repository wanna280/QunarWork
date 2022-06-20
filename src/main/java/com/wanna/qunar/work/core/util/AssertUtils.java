package com.wanna.qunar.work.core.util;

/**
 * 断言工具类
 *
 * @author wanna
 */
public abstract class AssertUtils {
    public static void assertState(boolean state, String message) {
        if (!state) {
            throw new IllegalStateException(message);
        }
    }
}

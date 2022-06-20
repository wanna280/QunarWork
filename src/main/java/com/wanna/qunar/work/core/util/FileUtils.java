package com.wanna.qunar.work.core.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * FileUtils for File Operation
 *
 * @author wanna
 */
public abstract class FileUtils {

    public static final String FILE_RESOURCE_PATH_PREFIX = "file:";

    public static void writeTo(String content, String destinationPath) throws IOException {
        writeTo(content.getBytes(StandardCharsets.UTF_8), resolvePath(destinationPath));
    }

    public static void writeTo(byte[] content, String destinationPath) throws IOException {
        try (final FileOutputStream stream = new FileOutputStream(resolvePath(destinationPath))) {
            stream.write(content);
        }
    }

    public static String readAsString(String sourcePath) throws IOException {
        return new String(readFrom(resolvePath(sourcePath)), StandardCharsets.UTF_8);
    }

    public static byte[] readFrom(String sourcePath) throws IOException {
        try (final FileInputStream stream = new FileInputStream(resolvePath(sourcePath))) {
            return stream.readAllBytes();
        }
    }

    private static String resolvePath(String path) {
        if (path.startsWith(FILE_RESOURCE_PATH_PREFIX)) {
            return path.substring(FILE_RESOURCE_PATH_PREFIX.length());
        }
        return path;
    }
}

package com.wanna.qunar.work.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * 类路径的资源解析器
 *
 * @author wanna
 */
public class ClassPathResourceParser implements ResourceParser {

    public static final String CLASSPATH_PREFIX = "classpath:";

    private final ClassLoader classLoader = ClassLoader.getSystemClassLoader();

    @Override
    public boolean support(String path) {
        return path.startsWith(CLASSPATH_PREFIX);
    }

    @Override
    public String parseAsString(String path) throws IOException {
        return new String(parse(path));
    }

    @Override
    public byte[] parse(String path) throws IOException {
        final InputStream stream = classLoader.getResourceAsStream(path.substring(CLASSPATH_PREFIX.length()));
        if (stream == null) {
            throw new IOException("无法找到该文件");
        }
        return stream.readAllBytes();
    }
}

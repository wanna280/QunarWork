package com.wanna.qunar.work.core.io;

import java.io.IOException;
import java.util.Arrays;

/**
 * 资源解析器，负责解析某个路径下的资源(支持类路径"classpath:")
 *
 * @author wanna
 * @see ClassPathResourceParser
 * @see FilePathResourceParser
 */
public interface ResourceParser {

    // 默认的ResourceParser的单例对象
    ResourceParser DEFAULT = new DefaultResourceParser();

    boolean support(String path);

    String parseAsString(String path) throws IOException;

    default String[] parseAsStringLines(String path) throws IOException {
        return Arrays.stream(parseAsString(path).split("\n")).map(String::trim).toArray(String[]::new);
    }

    byte[] parse(String path) throws IOException;
}
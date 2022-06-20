package com.wanna.qunar.work.core.io;

import com.wanna.qunar.work.core.util.FileUtils;

import java.io.IOException;

/**
 * 文件路径的资源解析器
 */
public class FilePathResourceParser implements ResourceParser {

    public static final String FILE_RESOURCE_PATH_PREFIX = "file:";

    @Override
    public boolean support(String path) {
        return path.startsWith(FILE_RESOURCE_PATH_PREFIX);
    }

    @Override
    public String parseAsString(String path) throws IOException {
        return FileUtils.readAsString(path.substring(FILE_RESOURCE_PATH_PREFIX.length()));
    }

    @Override
    public byte[] parse(String path) throws IOException {
        return FileUtils.readFrom(path.substring(FILE_RESOURCE_PATH_PREFIX.length()));
    }
}

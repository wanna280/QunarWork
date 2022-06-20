package com.wanna.qunar.work.core.io;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 默认的资源解析器的实现，组合ClassPath/FilePath两种类型的方式去进行解析
 *
 * @author wanna
 */
public class DefaultResourceParser implements ResourceParser {

    private final List<ResourceParser> parsers = Lists.newArrayListWithCapacity(2);

    public DefaultResourceParser() {
        parsers.add(new ClassPathResourceParser());
        parsers.add(new FilePathResourceParser());
    }

    /**
     * 添加一个自定义的ResourceParser
     */
    public void addResourceParser(ResourceParser parser) {
        this.parsers.add(parser);
    }

    @Override
    public boolean support(String path) {
        for (ResourceParser parser : parsers) {
            if (parser.support(path)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String parseAsString(String path) throws IOException {
        for (ResourceParser parser : parsers) {
            if (parser.support(path)) {
                return parser.parseAsString(path);
            }
        }
        throw new IllegalStateException("无法找到合适的资源解析器去解析该资源文件[" + path + "]");
    }

    @Override
    public String[] parseAsStringLines(String path) throws IOException {
        for (ResourceParser parser : parsers) {
            if (parser.support(path)) {
                return parser.parseAsStringLines(path);
            }
        }
        throw new IllegalStateException("无法找到合适的资源解析器去解析该资源文件[" + path + "]");
    }

    @Override
    public byte[] parse(String path) throws IOException {
        for (ResourceParser parser : parsers) {
            if (parser.support(path)) {
                return parser.parse(path);
            }
        }
        throw new IllegalStateException("无法找到合适的资源解析器去解析该资源文件[" + path + "]");
    }
}

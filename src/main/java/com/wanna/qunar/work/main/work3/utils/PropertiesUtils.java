package com.wanna.qunar.work.main.work3.utils;

import com.google.common.collect.Maps;
import com.wanna.qunar.work.core.io.DefaultResourceParser;
import com.wanna.qunar.work.core.io.ResourceParser;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class PropertiesUtils {

    private static final ResourceParser parser = new DefaultResourceParser();

    /**
     * 加载配置文件，并以有序的Map(LinkedHashMap)的方式返回
     *
     * @param path 文件路径
     * @return 解析完成的Properties-Map(String,String)
     */
    public static LinkedHashMap<String, String> loadAsOrderedMap(String path) throws IOException {
        LinkedHashMap<String, String> properties = Maps.newLinkedHashMapWithExpectedSize(64);
        final String[] propertyLines = parser.parseAsStringLines(path);
        Arrays.stream(propertyLines).forEach(property -> {
            final String[] propertyKeyValues = Arrays.stream(property.split("\t")).map(String::trim).toArray(String[]::new);
            properties.put(propertyKeyValues[0], propertyKeyValues[1]);
        });
        return properties;
    }
}

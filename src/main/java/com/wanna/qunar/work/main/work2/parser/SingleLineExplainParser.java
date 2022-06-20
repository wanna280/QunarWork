package com.wanna.qunar.work.main.work2.parser;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * 单行注释的解析器
 */
public class SingleLineExplainParser implements TokenParser {

    public static final String SINGLE_LINE_EXPLAIN_PREFIX = "//";

    private int count = 0;

    private final List<String> singleLineExplains = Lists.newArrayListWithCapacity(64);

    @Override
    public int parse(String[] lines, int currentIndex) {
        if (lines[currentIndex].trim().startsWith(SINGLE_LINE_EXPLAIN_PREFIX)) {
            count++;
            singleLineExplains.add(lines[currentIndex]);
            return currentIndex;
        }
        return NO_MATCH;
    }

    public List<String> getSingleLineExplains() {
        return Lists.newArrayList(singleLineExplains);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "{SingleLineExplainCount=[" + count + "]}";
    }
}
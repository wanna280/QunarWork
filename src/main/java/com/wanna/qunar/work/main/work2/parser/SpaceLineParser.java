package com.wanna.qunar.work.main.work2.parser;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * 空白行的解析器
 */
public class SpaceLineParser implements TokenParser {
    private int count = 0;

    private final List<Integer> lineNumbers = Lists.newArrayList(64);

    @Override
    public int parse(String[] lines, int currentIndex) {
        final String line = lines[currentIndex];
        if (line.trim().isEmpty()) {
            count++;
            lineNumbers.add(currentIndex);
            return currentIndex;
        }
        return NO_MATCH;
    }

    public List<Integer> getLineNumbers() {
        return Lists.newArrayList(lineNumbers);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "{SpaceLineCount=[" + count + "]}";
    }
}
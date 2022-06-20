package com.wanna.qunar.work.main.work2.parser;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 多行注释的解析器
 */
public class MultiLineExplainParser implements TokenParser {

    public static final String MULTI_LINE_EXPLAIN_PREFIX = "/*";

    public static final String MULTI_LINE_EXPLAIN_SUFFIX = "*/";

    private int count = 0;

    // 多行注释块
    private final List<String> multiLineExplainBlock = Lists.newArrayListWithCapacity(128);

    // 空白行的index
    private final List<Integer> spaceLineNumbers = Lists.newArrayListWithCapacity(64);

    @Override
    public int parse(String[] lines, int currentIndex) {
        if (!lines[currentIndex].trim().startsWith(MULTI_LINE_EXPLAIN_PREFIX)) {
            return NO_MATCH;
        }
        for (int index = currentIndex; index < lines.length; index++) {
            if (lines[index].trim().isEmpty()) {
                spaceLineNumbers.add(index);
            }
            if (lines[index].trim().endsWith(MULTI_LINE_EXPLAIN_SUFFIX)) {
                count += (index - currentIndex + 1);
                multiLineExplainBlock.add(String.join("\n", Arrays.stream(lines).collect(Collectors.toList()).subList(currentIndex, index + 1)));
                return index;
            }
        }
        return lines.length - 1;  // 到结束都没有找到"*/"的话
    }

    public List<String> getMultiLineExplainBlock() {
        return Lists.newArrayList(this.multiLineExplainBlock);
    }

    public List<Integer> getSpaceLineNumbers() {
        return Lists.newArrayList(this.spaceLineNumbers);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "{MultiLineExplainCount=[" + count + "], Include SpaceLine Count=[" + spaceLineNumbers.size() + "]}";
    }
}
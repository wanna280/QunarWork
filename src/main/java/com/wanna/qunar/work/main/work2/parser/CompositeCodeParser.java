package com.wanna.qunar.work.main.work2.parser;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 组合的代码解析器，符合多行注释、单行注释以及空白行的解析器去组合完成
 *
 * @see MultiLineExplainParser
 * @see SingleLineExplainParser
 * @see SpaceLineParser
 *
 * @author wanna
 */
public class CompositeCodeParser implements TokenParser {
    private final List<TokenParser> tokenParsers = Lists.newArrayListWithCapacity(3);

    {
        addTokenParser(new MultiLineExplainParser());
        addTokenParser(new SpaceLineParser());
        addTokenParser(new SingleLineExplainParser());
    }

    public void addTokenParser(TokenParser parser) {
        this.tokenParsers.add(parser);
    }


    // 代码行
    private final List<String> codeLines = new ArrayList<>();

    @Override
    public int parse(String[] lines, int currentIndex) {
        int index = 0;
        while (index < lines.length) {
            int result = -1;
            // 遍历所有的CodeParser去进行解析
            for (TokenParser tokenParser : tokenParsers) {
                result = tokenParser.parse(lines, index);
                if (result != NO_MATCH) {
                    break;
                }
            }
            // 如果index == NO_MATCH，那么说明是代码行
            if (result == NO_MATCH) {
                codeLines.add(lines[index] + "\n");
                index++;
            } else { // 如果匹配了，那么跳到匹配结果的下一行当中...
                index = result + 1;
            }
        }
        return lines.length;
    }

    public List<String> getCodeLines() {
        return Lists.newArrayList(this.codeLines);
    }

    @Override
    public int getCount() {
        return tokenParsers.stream().map(TokenParser::getCount).mapToInt(Integer::intValue).sum() + codeLines.size();
    }

    @Override
    public String toString() {
        return tokenParsers.stream().map(TokenParser::toString).collect(Collectors.joining(", ")) + ", {CodeLine=[" + codeLines.size() + "]}";
    }
}
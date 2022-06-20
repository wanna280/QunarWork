package com.wanna.qunar.work.main.work4.parser;

import com.wanna.qunar.work.core.io.ResourceParser;

import java.io.IOException;
import java.util.Arrays;

import static com.wanna.qunar.work.main.work4.parser.constants.CommandConstants.GREP_COMMAND;

/**
 * Grep命令的解析器
 *
 * @author wanna
 */
public class GrepPipeParser implements ShellPipeParser {
    @Override
    public boolean support(String[] command) {
        return GREP_COMMAND.equals(command[0]) && (command.length == 2 || command.length == 3);
    }

    @Override
    public String[] parse(String[] origin, String[] command) {
        // 如果如果有三个参数，那么第三个参数为给定的fileName
        if (command.length == 3) {
            try {
                origin = ResourceParser.DEFAULT.parseAsStringLines(command[2]);
            } catch (IOException e) {
                throw new IllegalStateException("无法解析到给定的文件[" + command[2] + "], 原始的command=" + String.join(" ", origin));
            }
        }
        final String keyword = command[1];
        return Arrays.stream(origin).filter(str -> str.contains(keyword)).toArray(String[]::new);
    }
}
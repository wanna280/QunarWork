package com.wanna.qunar.work.main.work4.parser;

import java.util.Arrays;

import static com.wanna.qunar.work.main.work4.parser.constants.CommandConstants.GREP_COMMAND;

/**
 * Grep命令的解析器
 */
public class GrepPipeParser implements ShellPipeParser {
    @Override
    public boolean support(String[] command) {
        return command[0].equals(GREP_COMMAND) && command.length == 2;
    }

    @Override
    public String[] parse(String[] origin, String[] command) {
        final String keyword = command[1];
        return Arrays.stream(origin).filter(str -> str.contains(keyword)).toArray(String[]::new);
    }
}
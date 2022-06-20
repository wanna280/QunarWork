package com.wanna.qunar.work.main.work4.parser;

import com.wanna.qunar.work.core.io.ResourceParser;

import java.io.IOException;

import static com.wanna.qunar.work.main.work4.parser.constants.CommandConstants.WC_COMMAND;

/**
 * Wc的管道解析器
 *
 * @author wanna
 */
public class WcPipeParser implements ShellPipeParser {
    @Override
    public boolean support(String[] command) {
        if (!WC_COMMAND.equals(command[0])) {
            return false;
        }
        if (!"-l".equals(command[1])) {
            return false;
        }
        return command.length == 2 || command.length == 3;
    }

    @Override
    public String[] parse(String[] origin, String[] command) {
        if (command.length == 2) {
            return new String[]{String.valueOf(origin.length)};
        }
        final String file;
        try {
            file = ResourceParser.DEFAULT.parseAsString(command[2]);
            return new String[]{String.valueOf(file.split("\n").length)};
        } catch (IOException e) {
            throw new IllegalStateException("无法找到目标文件：[" + command[2] + "]");
        }
    }
}
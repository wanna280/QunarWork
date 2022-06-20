package com.wanna.qunar.work.main.work4.parser;

import com.wanna.qunar.work.core.io.DefaultResourceParser;

import java.io.IOException;

import static com.wanna.qunar.work.main.work4.parser.constants.CommandConstants.WC_COMMAND;

/**
 * Wc的管道解析器
 */
public class WcPipeParser implements ShellPipeParser {
    @Override
    public boolean support(String[] command) {
        if (!command[0].equals(WC_COMMAND)) {
            return false;
        }
        if (!command[1].equals("-l")) {
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
            file = new DefaultResourceParser().parseAsString(command[2]);
            return new String[]{String.valueOf(file.split("\n").length)};
        } catch (IOException e) {
            throw new IllegalStateException("无法找到目标文件：[" + command[2] + "]");
        }
    }
}
package com.wanna.qunar.work.main.work4.parser;

import com.wanna.qunar.work.core.io.DefaultResourceParser;

import java.io.IOException;

import static com.wanna.qunar.work.main.work4.parser.constants.CommandConstants.CAT_COMMAND;

/**
 * Cat命令的解析器
 */
public class CatCommandParser implements ShellPipeParser {

    @Override
    public boolean support(String[] command) {
        return command[0].equals(CAT_COMMAND) && command.length == 2;
    }

    @Override
    public String[] parse(String[] origin, String[] command) {
        final DefaultResourceParser parser = new DefaultResourceParser();
        final String fileContent;
        try {
            fileContent = parser.parseAsString(command[1]);
        } catch (IOException ex) {
            throw new IllegalStateException("无法找到给定的文件[" + command[1] + "]");
        }
        return fileContent.split("\n");
    }
}
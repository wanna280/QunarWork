package com.wanna.qunar.work.main.work4.parser;

import com.wanna.qunar.work.core.io.ResourceParser;

import java.io.IOException;

import static com.wanna.qunar.work.main.work4.parser.constants.CommandConstants.CAT_COMMAND;

/**
 * Cat命令的解析器
 *
 * @author wanna
 */
public class CatCommandParser implements ShellPipeParser {

    @Override
    public boolean support(String[] command) {
        return CAT_COMMAND.equals(command[0]) && command.length == 2;
    }

    @Override
    public String[] parse(String[] origin, String[] command) {
        final String fileContent;
        try {
            fileContent = ResourceParser.DEFAULT.parseAsString(command[1]);
        } catch (IOException ex) {
            throw new IllegalStateException("无法找到给定的文件[" + command[1] + "]");
        }
        return fileContent.split("\n");
    }
}
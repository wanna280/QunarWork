package com.wanna.qunar.work.main.work4.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultShellParser implements ShellParser {
    private final List<ShellPipeParser> shellPipeParsers = new ArrayList<>();

    {
        addPipeParser(new CatCommandParser());
        addPipeParser(new GrepPipeParser());
        addPipeParser(new WcPipeParser());
    }

    public void addPipeParser(ShellPipeParser parser) {
        shellPipeParsers.add(parser);
    }

    @Override
    public String[] parse(String command) {
        final String[] commands = Arrays.stream(command.split("\\|")).map(String::trim).toArray(String[]::new);
        String[] result = new String[0];
        for (String comm : commands) {
            // 将每个命令的各个参数去进行解析...使用空格分割
            final String[] args = Arrays.stream(comm.split(" ")).map(String::trim).toArray(String[]::new);
            // 遍历所有的管道解析器，尝试去进行解析
            for (ShellPipeParser parser : shellPipeParsers) {
                if (parser.support(args)) {
                    result = parser.parse(result, args);
                    break;
                }
            }
        }
        return result;
    }
}
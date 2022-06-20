package com.wanna.qunar.work.main.work4.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 默认的ShellParser的实现，负责组合多个ShellPipeParser去进行解析
 *
 * @author wanna
 */
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
        // 使用管道分隔符去进行切割，得到每个命令的列表
        final String[] commands = Arrays.stream(command.split("\\|")).map(String::trim).toArray(String[]::new);
        String[] result = new String[0];  // empty array
        for (String comm : commands) {
            // 将每个命令的各个参数去进行解析...使用空格分割
            final String[] args = Arrays.stream(comm.split(" ")).map(String::trim).toArray(String[]::new);
            // 遍历所有的管道解析器，尝试去进行解析，直到找到一个可以去进行解析的管道解析器
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
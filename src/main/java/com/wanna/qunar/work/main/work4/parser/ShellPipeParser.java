package com.wanna.qunar.work.main.work4.parser;

/**
 * Shell管道的解析器
 */
public interface ShellPipeParser {
    /**
     * 是否支持处理这样的管道？
     *
     * @param command 命令列表
     * @return 是否支持处理？
     */
    boolean support(String[] command);

    /**
     * 如果必要的话，尝试解析管道
     *
     * @param origin  原始的要去进行解析的结果
     * @param command 命令列表
     * @return 对origin去进行处理的结果
     */
    String[] parse(String[] origin, String[] command);
}

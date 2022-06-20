package com.wanna.qunar.work.main.work4.parser;

/**
 * ShellParser
 *
 * @author wanna
 */
public interface ShellParser {

    ShellParser DEFAULT = new DefaultShellParser();

    String[] parse(String command);
}

package com.wanna.qunar.work.main.work4;

import com.wanna.qunar.work.main.work4.parser.ShellParser;

import java.util.Arrays;

/**
 * 模拟Shell管道
 *
 * @author wanna
 */
public class ShellPipeMain {
    public static final String STRING_UTILS_FILE_LOCATION = "classpath:pipe.txt";

    public static void main(String[] args) {
        String[] commands = Arrays.stream(new String[]{
                "cat {filename}",
                "cat {filename} | grep wanna",
                "grep wanna {filename}",
                "cat {filename} | grep wanna | wc -l",
                "wc -l {filename}"
        }).map(s -> s.replace("{filename}", STRING_UTILS_FILE_LOCATION)).toArray(String[]::new);

        Arrays.stream(commands).forEach(command -> System.out.println("command=[" + command + "], 执行结果为" + Arrays.toString(ShellParser.DEFAULT.parse(command))));
    }
}

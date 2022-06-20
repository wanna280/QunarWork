package com.wanna.qunar.work.main.work4;

import com.wanna.qunar.work.main.work4.parser.DefaultShellParser;

import java.util.Arrays;

/**
 * 模拟Shell管道
 */
public class ShellPipeMain {
    public static final String STRING_UTILS_FILE_LOCATION = "classpath:pipe.txt";

    public static void main(String[] args) {
        String[] commands = Arrays.stream(new String[]{
                "cat {filename}",
                "cat {filename} | grep wanna",
                "cat {filename} | grep wanna | wc -l",
                "wc -l {filename}"
        }).map(s -> s.replace("{filename}", STRING_UTILS_FILE_LOCATION)).toArray(String[]::new);


        for (String command : commands) {
            System.out.println(Arrays.toString(new DefaultShellParser().parse(command)));
        }
    }
}

package com.wanna.qunar.work.main.work2;

import com.wanna.qunar.work.core.io.DefaultResourceParser;
import com.wanna.qunar.work.main.work2.parser.CompositeCodeParser;

import java.io.IOException;

public class CodeCountMain {
    public static final String STRING_UTILS_FILE_LOCATION = "classpath:StringUtils.java";

    public static void main(String[] args) throws IOException {
        final DefaultResourceParser parser = new DefaultResourceParser();

        // 读取文件
        final String[] lines = parser.parseAsStringLines(STRING_UTILS_FILE_LOCATION);

        // 交给代码解析器去进行代码的解析
        final CompositeCodeParser codeParser = new CompositeCodeParser();
        codeParser.parse(lines, 0);

        // 输出解析的结果
        System.out.println(codeParser);
        System.out.println("总行数为[" + codeParser.getCount() + "]");
    }
}

package com.wanna.qunar.work.main.work2;

import com.wanna.qunar.work.core.io.ResourceParser;
import com.wanna.qunar.work.main.work2.parser.CompositeCodeParser;

import java.io.IOException;


/**
 *
 * @author wanna
 */
public class CodeCountMain {
    public static final String STRING_UTILS_FILE_LOCATION = "classpath:StringUtils.java";

    public static void main(String[] args) throws IOException {
        // 读取文件
        final String[] lines = ResourceParser.DEFAULT.parseAsStringLines(STRING_UTILS_FILE_LOCATION);

        // 交给代码解析器去进行代码的解析
        final CompositeCodeParser codeParser = new CompositeCodeParser();
        codeParser.parse(lines, 0);

        // 输出解析的结果
        System.out.println(codeParser);
        System.out.println("总行数为[" + codeParser.getCount() + "]");
    }
}

package com.wanna.qunar.work.main.work2.parser;

/**
 * Token解析器
 */
public interface TokenParser {
    int NO_MATCH = -1;  // 不支持匹配当前代码的常量标识符

    /**
     * 解析从currentIndex之后的代码
     *
     * @param lines        代码行列表
     * @param currentIndex 当前正在解析多少行
     * @return 如果支持解析，那么return endIndex；如果不支持解析，那么return NO_MATCH(-1)
     */
    int parse(String[] lines, int currentIndex);

    /**
     * 获取这个解析器解析的代码的行数
     *
     * @return 代码行数量
     */
    int getCount();
}
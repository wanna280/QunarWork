package com.wanna.qunar.work.main.work3.replacer;

import java.util.Map;

/**
 * 模板代码的替换器
 */
public interface TemplateReplacer {
    String[] replace(String[] lines, Map<String, String> properties);
}

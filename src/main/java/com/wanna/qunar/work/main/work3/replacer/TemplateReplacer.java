package com.wanna.qunar.work.main.work3.replacer;

import java.util.Map;

/**
 * 模板代码的替换器
 *
 * @author wanna
 */
public interface TemplateReplacer {

    // 默认的模板代码替换器的单例对象
    TemplateReplacer DEFAULT = new DefaultTemplateReplacer();

    String[] replace(String[] lines, Map<String, String> properties);
}

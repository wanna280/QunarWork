package com.wanna.qunar.work.main.work3.resolver;

import java.util.Map;

/**
 * 模板解析器，支持去解析"$function(index)"的表达式
 */
public interface TemplateResolver {

    // 默认的模板解析器的单例对象
    TemplateResolver DEFAULT = new DefaultTemplateResolver();

    boolean supports(String template);

    String resolve(String template, Map<String, String> properties);
}

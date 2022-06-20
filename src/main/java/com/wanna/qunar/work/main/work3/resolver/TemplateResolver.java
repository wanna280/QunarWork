package com.wanna.qunar.work.main.work3.resolver;

import java.util.Map;

/**
 * 模板解析器，支持去解析"$function(index)"的表达式
 */
public interface TemplateResolver {

    boolean supports(String template);

    String resolve(String template, Map<String, String> properties);
}

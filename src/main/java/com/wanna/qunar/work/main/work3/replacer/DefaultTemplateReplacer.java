package com.wanna.qunar.work.main.work3.replacer;

import com.google.common.collect.Lists;
import com.wanna.qunar.work.core.util.AssertUtils;
import com.wanna.qunar.work.main.work3.resolver.AbstractTemplateResolver;
import com.wanna.qunar.work.main.work3.resolver.TemplateResolver;

import java.util.List;
import java.util.Map;

/**
 * 默认的模板代码替换器的实现
 *
 * @see TemplateReplacer
 */
public class DefaultTemplateReplacer implements TemplateReplacer {

    private TemplateResolver resolver = TemplateResolver.DEFAULT;

    public void setResolver(TemplateResolver resolver) {
        this.resolver = resolver;
    }

    /**
     * // TODO 文件行数很多，可以考虑使用ForkJoin思想去进行实现，将任务拆分给多个线程去进行执行，执行完成之后再进行合并
     */
    @Override
    public String[] replace(String[] lines, Map<String, String> properties) {
        final List<String> resultLines = Lists.newArrayListWithCapacity(properties.size());
        // 遍历每一行的数据，挨个去进行处理
        for (String templateLine : lines) {
            // 首先，我们先去尝试找到"$"位置的索引
            int placeholderMarker = templateLine.indexOf(AbstractTemplateResolver.DEFAULT_TEMPLATE_FUNCTION_MARKER);

            // 遍历当前行当中的所有的占位符去进行解析(如果存在多个模板占位符，我们会在循环当中去进行挨个检查)
            while (placeholderMarker != -1) {
                // 如果存在有"$"，那么我们就得开始去寻找占位符的后缀中的")"的位置
                final int placeholderSuffix = templateLine.indexOf(AbstractTemplateResolver.DEFAULT_TEMPLATE_SUFFIX_MARKER, placeholderMarker);

                // 在找到了"$"和")"之后，我们就可以去切取"$function(index)"的模板占位符
                final String template = templateLine.substring(placeholderMarker, placeholderSuffix + 1);

                // 交给模板代码解析器去进行解析占位符的结果
                final String resolve = resolver.resolve(template, properties);
                AssertUtils.assertState(resolve != null, "找到了无法处理的行-->[" + templateLine + "]");

                // 将解析完成的模板占位符的值，去替换掉原本的占位符
                templateLine = templateLine.replace(template, resolve);

                // 重新计算placeholderMarker，尝试去进行下一个模板占位符的解析(如果存在有的话)
                placeholderMarker = templateLine.indexOf(AbstractTemplateResolver.DEFAULT_TEMPLATE_FUNCTION_MARKER);
            }

            // 当跳出循环时，说明已经不存在有"$"了，说明已经没有占位符，直接去进行添加到结果当中即可
            resultLines.add(templateLine);
        }
        return resultLines.toArray(String[]::new);  // toArray
    }
}

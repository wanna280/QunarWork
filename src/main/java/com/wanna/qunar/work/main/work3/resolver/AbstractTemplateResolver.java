package com.wanna.qunar.work.main.work3.resolver;

import java.util.List;
import java.util.Map;

/**
 * 抽象的模板解析器，提供了各种模板解析器的各个模板操作
 *
 * @author wanna
 */
public abstract class AbstractTemplateResolver implements TemplateResolver {

    public static final String DEFAULT_TEMPLATE_FUNCTION_MARKER = "$";

    public static final String DEFAULT_TEMPLATE_PREFIX_MARKER = "(";

    public static final String DEFAULT_TEMPLATE_SUFFIX_MARKER = ")";

    private final String prefix;

    private final String prefixMarker;

    private final String suffixMarker;

    public AbstractTemplateResolver(String prefix, String prefixMarker, String suffixMarker) {
        this.prefix = prefix;
        this.prefixMarker = prefixMarker;
        this.suffixMarker = suffixMarker;
    }

    public AbstractTemplateResolver(String prefix) {
        this(prefix, DEFAULT_TEMPLATE_PREFIX_MARKER, DEFAULT_TEMPLATE_SUFFIX_MARKER);
    }

    @Override
    public boolean supports(String template) {
        return template.startsWith(prefix + prefixMarker) && template.endsWith(suffixMarker);
    }

    @Override
    public String resolve(String template, Map<String, String> properties) {
        // 把"$function(index)"中的，"$function("和")"切掉，取得index
        final String indexString = template.substring((prefix + prefixMarker).length(), template.length() - suffixMarker.length());
        return getOrderedProperties(properties).get(Integer.parseInt(indexString));
    }

    /**
     * 模板方法，实现我的子类你得告诉我，如何去进行排序，我就可以根据index作为排好序的数组的index去进行获取了呀！
     *
     * @param properties properties
     * @return 排好序的Properties
     */
    protected abstract List<String> getOrderedProperties(Map<String, String> properties);
}

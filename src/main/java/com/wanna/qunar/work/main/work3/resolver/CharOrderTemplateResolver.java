package com.wanna.qunar.work.main.work3.resolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * charOrder的模板解析器，对所有的Properties去按照字符去进行排序即可
 */
public class CharOrderTemplateResolver extends AbstractTemplateResolver {

    public static final String CHAR_ORDER_TEMPLATE_PREFIX = "$charOrder";


    public CharOrderTemplateResolver() {
        super(CHAR_ORDER_TEMPLATE_PREFIX);
    }

    private List<String> orderedProperties;

    @Override
    protected List<String> getOrderedProperties(Map<String, String> properties) {
        if (orderedProperties == null) {
            this.orderedProperties = new ArrayList<>(properties.values());
            this.orderedProperties.sort(String::compareTo);  // use String Comparator
        }
        return orderedProperties;
    }
}

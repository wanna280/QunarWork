package com.wanna.qunar.work.main.work3.resolver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * charOrderDESC的模板解析器，对所有的Properties去按照字符去进行排序并取反即可
 */
public class CharOrderDescTemplateResolver extends AbstractTemplateResolver {

    public static final String CHAR_ORDER_DESC_TEMPLATE_PREFIX = "$charOrderDESC";

    public CharOrderDescTemplateResolver() {
        super(CHAR_ORDER_DESC_TEMPLATE_PREFIX);
    }

    private List<String> orderedProperties;

    @Override
    protected List<String> getOrderedProperties(Map<String, String> properties) {
        if (orderedProperties == null) {
            this.orderedProperties = new ArrayList<>(properties.values());
            this.orderedProperties.sort(Comparator.reverseOrder()); // use reverseOrder
        }
        return orderedProperties;
    }
}

package com.wanna.qunar.work.main.work3.resolver;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * natureOrder的模板解析器，按照给定的index去进行return即可，"$natureOrder(index)"中的index也就是数组的索引
 *
 * @author wanna
 */
public class NatureOrderTemplateResolver extends AbstractTemplateResolver {

    public static final String NATURE_ORDER_TEMPLATE_PREFIX = "$natureOrder";

    public NatureOrderTemplateResolver() {
        super(NATURE_ORDER_TEMPLATE_PREFIX);
    }


    private ArrayList<String> orderedProperties;

    @Override
    protected List<String> getOrderedProperties(Map<String, String> properties) {
        if (orderedProperties == null) {
            orderedProperties = Lists.newArrayList(properties.values());
        }
        return orderedProperties;
    }
}

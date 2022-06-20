package com.wanna.qunar.work.main.work3.resolver;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * indexOrder的模板解析器，会按照前面的数字去进行排序，再根据index去进行获取到对应位置的内容，我们需要向父类提供的是排好序的Properties
 */
public class IndexOrderTemplateResolver extends AbstractTemplateResolver {

    public static final String INDEX_ORDER_TEMPLATE_PREFIX = "$indexOrder";

    public IndexOrderTemplateResolver() {
        super(INDEX_ORDER_TEMPLATE_PREFIX);
    }

    // 通过value获取到Key的Map
    private BiMap<String, String> biMap;

    // 排好序的Properties
    private List<String> orderedProperties;

    @Override
    protected List<String> getOrderedProperties(Map<String, String> properties) {
        if (this.biMap == null || this.orderedProperties == null) {
            this.biMap = HashBiMap.create(properties).inverse();
            this.orderedProperties = new ArrayList<>(properties.values());
            this.orderedProperties.sort((o1, o2) -> Integer.parseInt(biMap.get(o1)) - Integer.parseInt(biMap.get(o2)));
        }
        return orderedProperties;
    }
}

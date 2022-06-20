package com.wanna.qunar.work.main.work3.resolver;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 默认的TemplateResolver，负责去组合多个TemplateResolver去进行解析
 */
public class DefaultTemplateResolver implements TemplateResolver {
    private final List<TemplateResolver> templateResolvers = Lists.newArrayListWithCapacity(4);

    {
        addTemplateResolver(new CharOrderTemplateResolver());
        addTemplateResolver(new CharOrderDescTemplateResolver());
        addTemplateResolver(new IndexOrderTemplateResolver());
        addTemplateResolver(new NatureOrderTemplateResolver());
    }

    public void addTemplateResolver(TemplateResolver templateResolver) {
        this.templateResolvers.add(templateResolver);
    }

    @Override
    public boolean supports(String template) {
        for (TemplateResolver templateResolver : templateResolvers) {
            if (templateResolver.supports(template)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String resolve(String template, Map<String, String> properties) {
        for (TemplateResolver templateResolver : templateResolvers) {
            if (templateResolver.supports(template)) {
                return templateResolver.resolve(template, properties);
            }
        }
        return null;
    }
}

package com.wanna.qunar.work.main.work3;

import com.wanna.qunar.work.core.io.DefaultResourceParser;
import com.wanna.qunar.work.core.util.FileUtils;
import com.wanna.qunar.work.main.work3.replacer.DefaultTemplateReplacer;
import com.wanna.qunar.work.main.work3.replacer.TemplateReplacer;
import com.wanna.qunar.work.main.work3.utils.PropertiesUtils;

import java.io.IOException;
import java.util.Map;

public class DecoderMain {

    public static final String TEMPLATE_FILE_LOCATION = "classpath:sdxl_template.txt";

    public static final String PROPERTY_FILE_LOCATION = "classpath:sdxl_prop.txt";

    public static final String RESULT_FILE_LOCATION = "file:/Users/jianchaojia/Desktop/Code/java/Project/QunarWork/src/main/resources/sdxl.txt";

    public static void main(String[] args) throws IOException {

        // 加载prop文件
        final Map<String, String> properties = PropertiesUtils.loadAsOrderedMap(PROPERTY_FILE_LOCATION);

        // 加载template文件
        final String[] templateLines = new DefaultResourceParser().parseAsStringLines(TEMPLATE_FILE_LOCATION);

        // 交给TemplateReplacer去进行占位符的解析
        final TemplateReplacer replacer = new DefaultTemplateReplacer();
        final String[] resultLines = replacer.replace(templateLines, properties);

        // 将结果写出到本地文件当中
        FileUtils.writeTo(String.join("\n", resultLines), RESULT_FILE_LOCATION);
    }
}

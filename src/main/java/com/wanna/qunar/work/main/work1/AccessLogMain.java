package com.wanna.qunar.work.main.work1;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.wanna.qunar.work.core.io.ResourceParser;
import com.wanna.qunar.work.core.util.AssertUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author wanna
 */
public class AccessLogMain {
    public static final String ACCESS_LOG_PATH = "classpath:access.log";

    public static final String SPACE_MARKER = " ";

    public static final String PARAMETER_MARKER = "?";

    public static final String URL_SEPARATOR = "/";

    public static final String GET_REQUEST = "GET";

    public static final String POST_REQUEST = "POST";

    public static final int COUNT = 10;

    public static void main(String[] args) {
        String[] lines;
        try {
            lines = ResourceParser.DEFAULT.parseAsStringLines(ACCESS_LOG_PATH);
            // 1.打印访问数量
            printAccessCount(lines);

            // 2.获取访问量最多的10个API
            getMost10Api(lines);

            // 3.解析GET/POST的数量
            parseGetAndPostCount(lines);

            // 4.按照一级的URL对URL去进行分类
            classifyURIByFirstLevel(lines);
        } catch (IOException e) {
            throw new IllegalStateException("[ERROR]无法找到目标文件[" + ACCESS_LOG_PATH + "]");
        }
    }

    /**
     * 1.输出访问的数量
     */
    public static void printAccessCount(String[] lines) {
        System.out.println("1.访问数量为" + lines.length);
    }

    /**
     * 2.获取访问数量最多的10个API
     */
    public static void getMost10Api(String[] lines) {
        // 提取路径，将URL转换为路径(去掉开头的请求方式，去掉"?"之后的参数部分)
        final String[] paths = extractPaths(lines);

        Map<String, Integer> pathToCountMap = Maps.newHashMap();

        // 使用多值Map，存放count->List<Path>的映射
        Multimap<Integer, String> countToPathsMap = ArrayListMultimap.create();
        Arrays.stream(paths).forEach(path -> {
            // 获取之前这个path之前对应的次数
            final int count = pathToCountMap.getOrDefault(path, 0);
            // 设置path对应的次数为count+1
            pathToCountMap.put(path, count + 1);
            // 移除countToPaths当中的旧数据
            if (countToPathsMap.containsKey(count)) {
                countToPathsMap.remove(count, path);
            }
            // 尝试往count+1对应的Path列表里去添加元素
            countToPathsMap.put(count + 1, path);
        });

        // 对所有的访问次数去进行排序，顺序为从大到小
        final List<Integer> orderedCountList = countToPathsMap.keySet()
                .stream()
                .sorted((o1, o2) -> Integer.compare(o2, o1))
                .collect(Collectors.toList());

        // 我们尝试，从访问次数最大的路径开始去进行挨个检索，直到检索的数量到达指定的数量(10)，那么终止检索
        Map<String, Integer> result = Maps.newLinkedHashMap();  // use LinkedHashMap for order
        int index = 0;
        outer:
        while (true) {
            for (String mostPath : countToPathsMap.get(orderedCountList.get(index++))) {
                result.put(mostPath, pathToCountMap.get(mostPath));
                if (result.size() == COUNT) {
                    break outer;  // break outer
                }
            }
        }
        System.out.println("\n2.访问量最高的[" + COUNT + "]个API，以及对应的访问次数为：");
        result.forEach((k, v) -> System.out.println(k + "=" + v));
    }

    /**
     * 3.输出GET/POST请求的数量
     */
    public static void parseGetAndPostCount(String[] lines) {
        int getRequestCount = 0;
        int postRequestCount = 0;
        for (String line : lines) {
            if (line.startsWith(GET_REQUEST)) {
                getRequestCount++;
            } else if (line.startsWith(POST_REQUEST)) {
                postRequestCount++;
            } else {
                // ignored
            }
        }
        System.out.println("\n3.GET 请求的数量为[" + getRequestCount + "], POST请求的数量为=[" + postRequestCount + "]");
    }

    /**
     * 4.按照URL均为/AAA/BBB或者/AAA/BBB/CCC这样的格式，按照/AAA为类别去进行分类
     *
     * @param lines 文件当中的行的列表
     */
    public static void classifyURIByFirstLevel(String[] lines) {
        final Multimap<String, String> countMap = LinkedHashMultimap.create();
        final String[] paths = extractPaths(lines);
        for (String path : paths) {
            final int firstSeparator = path.indexOf(URL_SEPARATOR);
            AssertUtils.assertState(firstSeparator != -1, "[ERROR]URL不合法，path=[" + path + "]不是以'" + URL_SEPARATOR + "'作为开头的");
            final int secondSeparator = path.indexOf(URL_SEPARATOR, firstSeparator + 1);
            if (secondSeparator == -1) {
                countMap.put(path, path);
            } else {
                countMap.put(path.substring(0, secondSeparator), path);  // 切取一级的url作为key
            }
        }
        System.out.println("\n4.总的分类数量为:" + countMap.size());
    }

    /**
     * 提取出来路径(去掉开头的请求方式，去掉"?"之后的部分)
     */
    public static String[] extractPaths(String[] lines) {
        return Arrays.stream(lines).map(line -> {
            final int spaceIndex = line.indexOf(SPACE_MARKER);
            AssertUtils.assertState(spaceIndex != -1, "[ERROR]提取路径过程当中找到了错误的行[" + line + "]");
            line = line.substring(spaceIndex + 1);
            final int andIndex = line.indexOf(PARAMETER_MARKER);
            return andIndex == -1 ? line : line.substring(0, andIndex);
        }).toArray(String[]::new);
    }
}

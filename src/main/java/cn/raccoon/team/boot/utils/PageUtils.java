package cn.raccoon.team.boot.utils;

import java.util.Collections;
import java.util.List;

/**
 * @Author wangjie
 * @Description 分页工具类
 * @Date 16:06 2021年05月12日
 **/
public class PageUtils {

    /**
     * 对集合数据的分页
     * @Author wangjie
     * @Date 11:56 2021年05月25日
     * @param list
     * @param start
     * @param length
     * @return java.util.List
     **/
    public static List page(List list, int start, int length) {
        if (start >= list.size()) {
            return Collections.emptyList();
        } else {
            int end = Math.min(start + length, list.size());
            return list.subList(start, end);
        }
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysongy.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * @FileName: GroupUtil
 * @Encoding: UTF-8
 * @Package: com.sysongy.util
 * @Link: http://www.sysongy.com
 * @Created on: 2016年05月27日, 9:51
 * @Author: Dongqiang.Wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
public class GroupUtil {

    /**
     * 分组依据接口，用于集合分组时,获取分组依据
     *
     * @param <T>
     */
    public interface GroupBy<T> {

        T groupby(Object obj);
    }

    /**
     * 分组
     *
     * @param colls 要分组的集合
     * @param gb 分组依据
     * @return
     */
    public static final <T extends Comparable<T>, D> Map<T, List<D>> group(Collection<D> colls, GroupBy<T> gb) {
        //分组集合不能为空
        if (colls == null || colls.isEmpty()) {
            System.out.println("分組集合不能為空!");
            return null;
        }
        //分组依据不能为null
        if (gb == null) {
            System.out.println("分組依據接口不能為Null!");
            return null;
        }
        Iterator<D> iter = colls.iterator();
        Map<T, List<D>> map = new HashMap<T, List<D>>();
        while (iter.hasNext()) {
            D d = iter.next();
            T t = gb.groupby(d);
            if (map.containsKey(t)) {
                map.get(t).add(d);
            } else {
                List<D> list = new ArrayList<D>();
                list.add(d);
                map.put(t, list);
            }
        }
        return map;
    }

    public static final <T extends Comparable<T>, D> List<Map<T, D>> groupTree(Collection<D> colls, GroupBy<T> gb,String childKey) {
        //分组集合不能为空
        if (colls == null || colls.isEmpty()) {
            System.out.println("分組集合不能為空!");
            return null;
        }
        //分组依据不能为null
        if (gb == null) {
            System.out.println("分組依據接口不能為Null!");
            return null;
        }
        Iterator<D> iter = colls.iterator();
        Map<T, List<D>> map = group(colls, gb);
        List<Map<T, D>> list = new ArrayList<Map<T,D>>();
        
        while (iter.hasNext()) {
            D d = iter.next();
            T t = gb.groupby(d);
            if (map.containsKey(t)) {
                map.get(t).add(d);
            } else {
                List<D> l = new ArrayList<D>();
                l.add(d);
                
                map.put(t, l);
            }
        }
        return list;
    }
}

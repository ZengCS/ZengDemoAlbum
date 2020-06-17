package com.zcs.demo.album.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by ZengCS on 2019/5/30.
 * E-mail:zengcs@vip.qq.com
 * Add:成都市天府软件园E3
 */
public final class JListKit {
    public static final String Split_Char = ",";

    public JListKit() {
    }

    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList();
    }

    public static <E> ArrayList<E> newArrayList(E... elements) {
        ArrayList<E> list = new ArrayList();
        Collections.addAll(list, elements);
        return list;
    }

    public static <E> ArrayList<E> newArrayListWithCapacity(int initialArraySize) {
        return new ArrayList(initialArraySize);
    }

    public static <E> LinkedList<E> newLinkedList() {
        return new LinkedList();
    }

    public static <E> LinkedList<E> newLinkedList(Iterable<? extends E> elements) {
        LinkedList<E> list = newLinkedList();
        Iterator var2 = elements.iterator();

        while(var2.hasNext()) {
            E element = (E) var2.next();
            list.add(element);
        }

        return list;
    }

    public static boolean isEmpty(Collection<?> list) {
        return list == null || list.size() <= 0;
    }

    public static boolean isNotEmpty(Collection<?> list) {
        return !isEmpty(list);
    }

    public static String converToString(String[] ig) {
        return converToString(ig, ",");
    }

    public static String converToString(String[] ig, String splitChar) {
        if (splitChar == null) {
            splitChar = ",";
        }

        StringBuilder sb = new StringBuilder();
        if (ig != null && ig.length > 0) {
            for(int i = 0; i < ig.length; ++i) {
                sb.append(ig[i]).append(splitChar);
            }
        }

        String str = sb.toString();
        if (str.endsWith(splitChar)) {
            str = str.substring(0, str.length() - 1);
        }

        return str;
    }

    public static List<String> converToArray(String str) {
        return converToArray(str, ",");
    }

    public static List<String> converToArray(String str, String splitChar) {
        if (splitChar == null) {
            splitChar = ",";
        }

        if (str != null) {
            String[] strs = str.split(splitChar);
            return new ArrayList(Arrays.asList(strs));
        } else {
            return new ArrayList();
        }
    }

    public static String listToString(List list, String splitChar) {
        if (splitChar == null) {
            splitChar = ",";
        }

        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            for(int i = 0; i < list.size(); ++i) {
                if (i < list.size() - 1) {
                    sb.append(list.get(i) + splitChar);
                } else {
                    sb.append(list.get(i));
                }
            }
        }

        String sbStr = sb.toString();
        if (sbStr.endsWith(splitChar)) {
            sbStr = sbStr.substring(0, sbStr.length() - 1);
        }

        return sbStr;
    }

    public static List<String> removeDuplicate(List<String> list) {
        Set set = new LinkedHashSet();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }
}
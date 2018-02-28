package com.wlt.collection;

import java.util.*;

/**
 * 迭代器使用
 * @author 魏霖涛
 * @since 2018/2/28 0028
 */
public class IteratorUtil {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList(Arrays.asList(1,2,3,4,5,6));
        System.out.println("Iterator============");
        Iterator iterator = list.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
        System.out.println("ListIterator============");
        ListIterator<Integer> listIterator = list.listIterator();
        while (listIterator.hasNext()){
            int next = listIterator.next();
            debug("next",next);
//            debug("next-index",listIterator.nextIndex());
//            listIterator.set(11);
//            listIterator.add(next+7);
        }
        while (listIterator.hasPrevious()){
//            debug("previous-index",listIterator.previousIndex());
            debug("previous",listIterator.previous());
        }
    }
    private static void debug(String str,Object object){
        System.out.printf("%20s",str+":");
        System.out.println(object);
    }
}

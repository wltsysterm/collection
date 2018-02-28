package com.wlt.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author 魏霖涛
 * @since 2018/2/28 0028
 */
public class Clone {
    public static void main(String[] args) throws CloneNotSupportedException {
        System.out.println("clone-对象使用-------------深度拷贝");
        WltClone wltClone = new WltClone("wlt");
        WltClone clone = wltClone.clone();
        System.out.println("modify---之前");
        System.out.println(wltClone.toString());
        System.out.println(clone.toString());
        wltClone.setName("modify-wlt");
        System.out.println("modify---之后");
        System.out.println(wltClone.toString());
        System.out.println(clone.toString());

        System.out.println("clone-集合使用-------------浅层拷贝");
        //Arrays.asList("1","2")返回的是只读的集合，而new ArrayList<>(Arrays.asList("1","2"))返回的是正常的集合
        ArrayList list = new ArrayList(Arrays.asList(wltClone,"2"));
        ArrayList clonelist = (ArrayList) list.clone();
        System.out.println("modify---之前");
        System.out.println(list);
        System.out.println(clonelist);
        //下面这种是把集合元素的引用所指的对象属性改变，所以会引发连锁改变
        ((WltClone)list.get(0)).setName("modify");
        //下面这种是直接把集合的引用给改变了，所以不会引发连锁改变
//        list.set(0,new WltClone("happy"));
        System.out.println("modify---之后");
        System.out.println(list);
        System.out.println(clonelist);
    }
}

/**
 * 不实现Clonealbe接口就会报下面这个错误
 * Exception in thread "main" java.lang.CloneNotSupportedException: com.wlt.collection.WltClone
 */
class WltClone implements Cloneable{
    private String name;

    public WltClone(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * clone()是protected范围，那么如果内部类没有重写话，其他地方实例化的内部类是没有clonef方法的
     * 能够访问他的protected成员的地方只有从class定义后的那个'{'直到与它匹配的'}'
     * 重写，拓宽了访问范围
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected WltClone clone() throws CloneNotSupportedException {
        return (WltClone) super.clone();
    }

    @Override
    public String toString() {
        return "WltClone{" +
                "name='" + name + '\'' +
                '}';
    }
}

package com.wlt.collection;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 集合的普通拷贝克隆复制都是浅层的，下面介绍一种基于对象流的深度拷贝
 * @author 魏霖涛
 * @since 2018/2/28 0028
 */
public class DeepCopy {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        WltDeepcopy wltClone = new WltDeepcopy("wlt");

        System.out.println("deepCopy-集合使用-------------深度拷贝");
        //Arrays.asList("1","2")返回的是只读的集合，而new ArrayList<>(Arrays.asList("1","2"))返回的是正常的集合
        ArrayList list = new ArrayList(Arrays.asList(wltClone,"2"));
        ArrayList clonelist = (ArrayList) deepCopyList(list);
        System.out.println("modify---之前");
        System.out.println(list);
        System.out.println(clonelist);
        System.out.println(list.hashCode());
        System.out.println(clonelist.hashCode());
        //下面这种是把集合元素的引用所指的对象属性改变，所以会引发连锁改变
        ((WltDeepcopy)list.get(0)).setName("modify");
        //下面这种是直接把集合的引用给改变了，所以不会引发连锁改变
//        list.set(0,new WltClone("happy"));
        System.out.println("modify---之后");
        System.out.println(list);
        System.out.println(clonelist);
    }
    public static <T> List<T> deepCopyList(List<T> source) throws IOException, ClassNotFoundException {
        List<T> target;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(source);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        target = (List<T>) objectInputStream.readObject();
        return  target;
    }
}
class WltDeepcopy implements Serializable{
    private String name;

    public WltDeepcopy(String name) {
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

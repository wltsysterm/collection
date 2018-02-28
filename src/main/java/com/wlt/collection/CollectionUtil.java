package com.wlt.collection;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author 魏霖涛
 * @since 2018/2/27 0027
 */
public class CollectionUtil {
    /**
     * 集合工具类使用
     * @param args
     */
    public static void main(String[] args) {
        List<Integer> list = (new LinkedList());
        //浅拷贝 只拷贝引用
        Collections.addAll(list, new Integer[]{0,1, 2, 3, 4, 5, 6});
        debug("addAll",list);
        Collections.swap(list,0,1);
        debug("swap",list);
        //默认升序
        Collections.sort(list);
        debug("sort",list);
        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                //降序
                return o2.compareTo(o1);
            }
        });
        debug("sortmyself",list);
        //测试不生效
//        Comparator<Integer> cmp = Collections.reverseOrder();
//        Collections.sort(list,cmp);
//        System.out.println(list);
        Collections.reverse(list);
        debug("reverse",list);
        Collections.replaceAll(list,0,11);
        debug("replaceAll",list);
        Collections.shuffle(list);
        debug("shuffle",list);
        Collections.shuffle(list,new Random(2));
        debug("shufflerand",list);
//        Collections.fill(list,20);
//        debug("fill",list);
        List<String> copyto = Arrays.asList("1,2,3,4,5,6,7,8,10".split(","));
        List<String> copyfrom = Arrays.asList("11,11,11".split(","));
        //浅拷贝
        Collections.copy(copyto,copyfrom);
        debug("copy",copyto);
        int min = Collections.min(list);
        debug("min",min);
        int min1 = Collections.min(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });
        debug("min-self",min1);
        int max = Collections.max(list);
        debug("max",max);
        Collections.sort(list);
        debug("sort-again",list);
        List<Integer> sub = Arrays.asList(2,3);
        //查找subList在list中首次出现位置的索引
        int subfirstindex = Collections.indexOfSubList(list,sub);
        debug("indexofsublist",subfirstindex);
        int sublastindex = Collections.lastIndexOfSubList(list,sub);
        debug("lastindexofsublist",sublastindex);
        Collections.rotate(list,2);
        debug("rotate",list);
        //使用二分搜索法，以获得指定对象在List中的索引，前提是集合已经排序
        //必须根据元素自然顺序对列表进行升级排序(即不能自定义比较器)
        //下面的list未经过排序，所以下面的搜索就出问题了
        int searchindex = Collections.binarySearch(list,1);
        debug("binarysearch",searchindex);
    }
    private static void debug(String str,Object object){
        System.out.printf("%20s",str+":");
        System.out.println(object);
    }

    @Test
    public void testCopy(){
        System.out.println("Collections.copy--浅层拷贝====modify之前=");
        WltCopy wltCopy = new WltCopy("wlt");
        List list = new LinkedList(Arrays.asList(wltCopy));
        //需要指定长度，不然会报错误IndexOutOfBoundsException
        List listcopy = new LinkedList(Arrays.asList(new Object[list.size()]));
        Collections.copy(listcopy,list);
        debug("list",list);
        debug("list-hashcode",list.hashCode());
        debug("listcopy",listcopy);
        debug("listcopy-hashcode",listcopy.hashCode());
        System.out.println("Collections.copy--浅层拷贝====modify之后=");
        ((WltCopy)list.get(0)).setName("wlt-modify");
        debug("list",list);
        debug("list-hashcode",list.hashCode());
        debug("listcopy",listcopy);
        debug("listcopy-hashcode",listcopy.hashCode());
        System.out.println(" new  ArrayList(List c)--浅层拷贝====modify之前=");
        List listnew = new LinkedList(list);
        debug("list",list);
        debug("list-hashcode",list.hashCode());
        debug("listnew",listnew);
        debug("listnew-hashcode",listnew.hashCode());
        System.out.println(" new  ArrayList(List c)--浅层拷贝====modify之后=");
        ((WltCopy)list.get(0)).setName("wlt-modify-new");
        debug("list",list);
        debug("list-hashcode",list.hashCode());
        debug("listnew",listnew);
        debug("listnew-hashcode",listnew.hashCode());
    }
    @Test
    public void test(){
        List list = (new LinkedList());
        ExecutorService pool = new ThreadPoolExecutor(10,10,30L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        int total = 100;
        while(total > 0){
            total--;
            pool.submit(new WltCallable(list));
            new Thread((Runnable) new WltCallable(list),"1");
        }
        pool.shutdown();
        while(true){
            if(pool.isTerminated()){
                break;
            }
        }
        System.out.println("=====**==**======");
        Iterator iterator = list.iterator();
        while(iterator.hasNext()){
            System.out.print(iterator.next()+"===");
        }
        System.out.println(list.size());
        System.out.println("=====**==**======");
    }
}
class WltCallable implements Callable{
    private Collection collection;

    public WltCallable(Collection collection) {
        this.collection = collection;
    }

    @Override
    public Object call() throws Exception {
        long l = System.currentTimeMillis();
        Thread.sleep(1000);
        System.out.print(Thread.currentThread().getName()+"===");
        collection.add(Thread.currentThread().getName());
        return null;
    }
}
class WltCopy{
    private String name;

    public WltCopy(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "wltCopy{" +
                "name='" + name + '\'' +
                '}';
    }
}

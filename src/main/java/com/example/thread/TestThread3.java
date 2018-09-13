package com.example.thread;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

//不能改动此Test类
public class TestThread3 extends Thread {
    private TestDo2 testDo2;
    private String key;
    private String value;

    public TestThread3(String key, String key2, String value) {
        this.testDo2 = TestDo2.getInstance();
            /*常量"1"和"1"是同一个对象，下面这行代码就是要用"1"+""的方式产生新的对象，
            以实现内容没有改变，仍然相等（都还为"1"），但对象却不再是同一个的效果*/
        this.key = key + key2;
        this.value = value;
    }

    public static void main(String[] args) throws InterruptedException {
        TestThread3 a = new TestThread3("1", "", "1");
        TestThread3 b = new TestThread3("1", "", "2");
        TestThread3 c = new TestThread3("3", "", "3");
        TestThread3 d = new TestThread3("4", "", "4");
        a.start();
        b.start();
        c.start();
        d.start();
    }

    public void run() {
        testDo2.doSome(key, value);
    }
}
class TestDo2 {
    private TestDo2() {
    }

    private static TestDo2 _instance = new TestDo2();

    public static TestDo2 getInstance() {
        return _instance;
    }

    private CopyOnWriteArrayList keys = new CopyOnWriteArrayList();

    public void doSome(Object key, String value) {
        //保证相同key是同一个key
        Object o = key;
        if(!keys.contains(key)){
            keys.add(o);
        }else{
            Iterator iterator = keys.iterator();
            for(keys.iterator(); iterator.hasNext();){
                Object next = iterator.next();
                if(next.equals(o)){
                    o = next;
                    break;
                }
            }
        }
        synchronized (o)
        // 以大括号内的是需要局部同步的代码，不能改动!
        {
            try {
                Thread.sleep(1000);
                System.out.println(key + ":" + value + ":"
                        + (System.currentTimeMillis() / 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
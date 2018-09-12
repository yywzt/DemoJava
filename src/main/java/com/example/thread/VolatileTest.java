package com.example.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yanzt
 * @date 2018/9/11 14:58
 * @describe
 */
public class VolatileTest {

    /**
     * volatile关键字能保证可见性没有错，但是上面的程序错在没能保证原子性。
     * 可见性只能保证每次读取的是最新的值，但是volatile没办法保证对变量的操作的原子性。
     * */

    public volatile int inc = 0;
    public void increase(){
        inc++;
    }

    //1
    /*public volatile AtomicInteger inc = new AtomicInteger();

    public void increase(){
        inc.getAndIncrement();
    }*/

    //2
    /*public int inc = 0;
    public synchronized void increase(){
        inc++;
    }*/

    //3
    /*public int inc = 0;
    Lock lock = new ReentrantLock();
    public void increase(){
        lock.lock();
        try {
            inc++;
        } finally {
            lock.unlock();
        }
    }*/

    public static void main(String[] args){
        final VolatileTest test = new VolatileTest();
        for(int i=0;i<10;i++) {
            new Thread(() -> {
                for (int j=0;j<100;j++) {
//                    System.out.println(test.inc);
                    test.increase();
                }
            }).start();
        }
        while (Thread.activeCount()>2)
            Thread.yield();
        System.out.println(test.inc);
    }
}

package com.example.thread;

import java.util.concurrent.CountDownLatch;

/**
 *  synchronized
 *      修饰实例方法，作用于当前实例加锁，进入同步代码前要获得当前实例的锁
 *
 * 修饰静态方法，作用于当前类对象加锁，进入同步代码前要获得当前类对象的锁
 *
 * 修饰代码块，指定加锁对象，对给定对象加锁，进入同步代码库前要获得给定对象的锁。
 *
 * */
public class TicketThread {

    static int ticket = 10;

    /*private synchronized void method(){
        if(ticket>0){
            System.out.println(Thread.currentThread().getName() + " : " + ticket--);
        }
    }*/
    private void method(){
        if(ticket>0){
            synchronized (this) {
                if (ticket > 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " : " + ticket--);
                }
            }
        }
    }

    private void print(){
//        synchronized(this) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//        }
    }

    public static void main(String[] args) throws Exception{
        TicketThread ticketThread = new TicketThread();
        CountDownLatch countDownLatch = new CountDownLatch(3);
        long start = System.currentTimeMillis();
        for (int i = 0;i<3;){
            new Thread(() -> {
//                while(ticket>0){
                    ticketThread.method();
                    ticketThread.print();
//                }
                //减少锁存器的计数，如果计数达到零，释放所有等待的线程。
                countDownLatch.countDown();
            },"站台"+ (++i)).start();
        }
        //导致当前线程等到锁存器计数到零，除非线程是 interrupted（阻断）
        countDownLatch.await();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}

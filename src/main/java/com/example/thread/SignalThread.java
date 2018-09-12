package com.example.thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SignalThread {

    print1 print1 = new print1();
    print2 print2 = new print2();

    public static void main(String[] args){
        SignalThread signalThread = new SignalThread();
//        signalThread.print1.aa();
        signalThread.print2.aa();
    }

    class myData{
        int data = 0;

        public void setData(int x,int y) {
            if(data==x){
                data = y;
            }
        }
    }

    /**
     * 甲线程输出：A、B、C、D、E
     * <p>
     * 乙线程输出：1、2、3、4、5
     * <p>
     * 丙线程数出：甲、乙、丙、丁、戊
     * <p>
     * 最终输出结果为（注：这是唯一可能的结果）
     * <p>
     * A 1 甲 B 2 乙 C 3 丙 D 4 丁 E 5 戊
     */
    class print1 {
        myData data = new myData();

        public void thread(List<String> name, int runflag, int nextflag) {
            new Thread(() -> {
                synchronized (data) {
                    for (int i = 0; i < name.size(); i++) {
                        while (data.data != runflag) {
                            try {
                                data.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.print(name.get(i) + " ");
//                    data.data = nextflag;
                        data.setData(data.data, nextflag);
                        data.notifyAll();
                    }
                }
            }).start();
        }

        public void aa() {
            List<List<String>> lists = new ArrayList<>();
            lists.add(new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E")));
            lists.add(new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5")));
            lists.add(new ArrayList<>(Arrays.asList("甲", "乙", "丙", "丁", "戊")));
            print1 print1 = new print1();
            for (int i = 0; i < 3; i++) {
                print1.thread(lists.get(i), i, (i + 1) % 3);
            }
        }
    }
    /**
     *
     * 借助同步机制、sleep()方法、join()方法，实现动画显示：
     *
     * 甲线程输出：1、3、5、7、9
     *
     * 乙线程输出：2、4、6、8、0
     *
     * 丙线程数出：a、b、c、d、e
     *
     * main线程输出：线程开始、线程结束
     *
     * 最终输出结果为（注：这是唯一可能的结果）
     *
     * 线程开始：1-2-a## 3-4-b## 5-6-c## 7-8-d## 9-0-e## 线程结束
     *
     * 要求：每隔一秒输出一个字符。（借助sleep）
     *
     * */
    class print2{
        myData data = new myData();

        public Thread thread(List<String> string, int runflag, int nextflag) {
            Thread thread = new Thread(() -> {
                synchronized (data) {
                    for (int i = 0; i < string.size(); i++) {
                        while (data.data != runflag) {
                            try {
                                data.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.print(string.get(i));
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (runflag == 2) {
                            System.out.print("#");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.print("#");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.print(" ");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.print("-");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        data.setData(data.data, nextflag);
                        data.notifyAll();
                    }
                }
            });
            return thread;
        }

        public void aa() {
            List<List<String>> lists = new ArrayList<>();
            lists.add(new ArrayList<>(Arrays.asList("1", "3", "5", "7", "9")));
            lists.add(new ArrayList<>(Arrays.asList("2", "4", "6", "8", "0")));
            lists.add(new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e")));
            print2 print2 = new print2();
            System.out.print("线程开始 : ");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread thread1 = print2.thread(lists.get(0), 0, 1);
            Thread thread2 = print2.thread(lists.get(1), 1, 2);
            Thread thread3 = print2.thread(lists.get(2), 2, 0);
            thread1.start();
            thread2.start();
            thread3.start();
            try {
                /**
                 * join的意思是使得放弃当前线程的执行，并返回对应的线程，例如下面代码的意思就是：
                 *          程序在main线程中调用t1线程的join方法，则main线程放弃cpu控制权，并返回t1线程继续执行直到线程t1执行完毕
                 *          所以结果是t1线程执行完后，才到主线程执行，相当于在main线程中同步t1线程，t1执行完了，main线程才有执行的机会
                 */
                thread3.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print(" 线程结束");
        }
    }
}

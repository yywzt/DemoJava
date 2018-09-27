package com.example.executor;

import java.util.concurrent.*;

/**
 * @author ywyw2424@foxmail.com
 * @date 2018/9/27 16:47
 * @desc 线程池与信号量
 */
public class TestExecutor {

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(5,10,200, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
//    ExecutorService executor=Executors.newFixedThreadPool(5) ;

    public static void main(String[] args){
        TestExecutor testExecutor = new TestExecutor();
        System.out.println("线程池中线程数目： " + testExecutor.executor.getPoolSize()+",等待执行的任务数: " + testExecutor.executor.getQueue().size()+",已执行完成的任务数目: " + testExecutor.executor.getCompletedTaskCount());
        for(int i=0;i<19;i++){
//            MyTask myTask = new MyTask(i);
            MyTask2 myTask = new MyTask2(i);
            testExecutor.executor.execute(myTask);
            System.out.println("线程池中线程数目： " + testExecutor.executor.getPoolSize()+",等待执行的任务数: " + testExecutor.executor.getQueue().size()+",已执行完成的任务数目: " + testExecutor.executor.getCompletedTaskCount());
        }
        testExecutor.executor.shutdown();
    }
}

class MyTask implements Runnable{

    private int taskNum;

    public MyTask(int taskNum) {
        this.taskNum = taskNum;
    }

    @Override
    public void run() {
        System.out.println("正在执行task" + taskNum);
        System.out.println("task" + taskNum + "执行完毕");
    }
}

class MyTask2 implements Runnable{

    private static Semaphore semaphore = new Semaphore(3);

    private int taskNum;

    public MyTask2(int taskNum) {
        this.taskNum = taskNum;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            System.out.println("正在执行task" + taskNum);
            Thread.sleep(6000);
            System.out.println("task" + taskNum + "执行完毕");
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
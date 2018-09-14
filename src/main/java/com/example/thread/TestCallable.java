package com.example.thread;

import java.util.concurrent.*;

public class TestCallable {

    static class SumTask implements Callable<Long> {

        @Override
        public Long call() throws Exception {

            long sum = 0;
            for (int i = 0; i < 9000; i++) {
                sum += i;
            }

            return sum;
        }
    }

    public static void main(String[] args) {
        System.out.println("Start:" + System.nanoTime());
        FutureTask<Long> futureTask = new FutureTask<Long>(new SumTask());
//        new Thread(futureTask).start();
        Executor executor= Executors.newSingleThreadExecutor();
        executor.execute(futureTask);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(futureTask.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("End:" + System.nanoTime());
    }
}

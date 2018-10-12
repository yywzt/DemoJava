package com.example.io;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ywyw2424@foxmail.com
 * @date 2018/10/12 11:34
 * @desc 通过Java IO创建管道
 * 可以通过Java IO中的PipedOutputStream和PipedInputStream创建管道。
 * 一个PipedInputStream流应该和一个PipedOutputStream流相关联。
 * 一个线程通过PipedOutputStream写入的数据可以被另一个线程通过相关联的PipedInputStream读取出来。
 */
public class PiPeExample {

    public static void main(String[] args) throws IOException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2));
//        ExecutorService executorService = Executors.newFixedThreadPool(2);

        final PipedOutputStream _output = new PipedOutputStream();
        final PipedInputStream _input = new PipedInputStream(_output);

//        final PipedOutputStream _output = new PipedOutputStream();
//        final PipedInputStream _input = new PipedInputStream();
//        _output.connect(_input);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try(PipedOutputStream output = _output) {
                    try {
                        System.out.println("write bytes");
                        output.write("hahahahah".getBytes());
                        Thread.sleep(3000);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try(PipedInputStream input = _input) {
                    int read;
                    try {
                        System.out.println("read bytes");
                        while ((read = input.read()) != -1) {
                            System.out.println((char) read);
                        }
                        Thread.sleep(3000);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("ahsd");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.shutdown();
    }


}

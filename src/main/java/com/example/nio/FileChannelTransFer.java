package com.example.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @author ywyw2424@foxmail.com
 * @date 2018/10/17 15:25
 * @desc
 */
public class FileChannelTransFer {

    public static void main(String[] args) throws IOException {
        RandomAccessFile accessFile1 = new RandomAccessFile("D:\\yw\\workspace\\ideaworkspace\\DemoJava\\src\\main\\java\\com\\example\\io\\test.txt","rw");
        FileChannel channel1 = accessFile1.getChannel();
        RandomAccessFile accessFile2 = new RandomAccessFile("D:\\yw\\workspace\\ideaworkspace\\DemoJava\\src\\main\\java\\com\\example\\nio\\testTo.txt","rw");
        FileChannel channel2 = accessFile2.getChannel();

        long position = 0;
        long count = channel1.size();
        //将数据从channel1通道传输至channel2通道中
         channel1.transferTo(position,count, channel2);
//        channel2.transferFrom(channel1,position,count);
    }
}

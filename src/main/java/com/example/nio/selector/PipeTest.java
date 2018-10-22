package com.example.nio.selector;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * @author ywyw2424@foxmail.com
 * @date 2018/10/22 16:41
 * @desc
 */
public class PipeTest {

    /**
     * Java NIO 管道是2个线程之间的单向数据连接。Pipe有一个source通道和一个sink通道。
     * 数据会被写到sink通道，从source通道读取。
     * */
    public static void main(String[] args) throws IOException {
        Pipe pipe = Pipe.open();
        //写入数据到sink通道
        Pipe.SinkChannel sinkChannel = pipe.sink();

        ByteBuffer buffer = ByteBuffer.allocate(48);
        buffer.clear();
        buffer.put("hello c".getBytes());

        buffer.flip();

        while (buffer.hasRemaining()) {
            sinkChannel.write(buffer);
        }

        //从source通道读取数据
        Pipe.SourceChannel sourceChannel = pipe.source();
        buffer.clear();
        int read = sourceChannel.read(buffer);//返回读取的字节数
        buffer.flip();
    }
}

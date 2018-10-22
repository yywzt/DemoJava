package com.example.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * @author ywyw2424@foxmail.com
 * @date 2018/10/22 16:25
 * @desc
 */
public class DatagramChannelTest {

    public static void main(String[] args) throws IOException {
        //打开DatagramChannel
        DatagramChannel channel = DatagramChannel.open();
        channel.bind(new InetSocketAddress(8000));

        //接收数据
        ByteBuffer buffer = ByteBuffer.allocate(48);
        buffer.clear();
        channel.receive(buffer);
        System.out.println(buffer);

        //发送数据
        buffer.clear();
        String newData = "New String to write to file..." + System.currentTimeMillis();
        buffer.put(newData.getBytes());
        buffer.flip();

        channel.send(buffer,new InetSocketAddress("127.0.0.1",8000));

        //连接到特定地址收发数据
        channel.connect(new InetSocketAddress("127.0.0.1",8000));
        int read = channel.read(buffer);
        int write = channel.write(buffer);
        System.out.println(buffer);
    }
}

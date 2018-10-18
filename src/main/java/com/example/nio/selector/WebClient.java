package com.example.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author ywyw2424@foxmail.com
 * @date 2018/10/17 17:51
 * @desc
 */
public class WebClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1",8000));

        ByteBuffer writeBuffer = ByteBuffer.allocate(32);
        ByteBuffer readBuffer = ByteBuffer.allocate(32);

        writeBuffer.put("hello c ".getBytes());
        writeBuffer.flip();

//        while (true){
        for(int i=0;i<10;i++){
            writeBuffer.rewind();
            socketChannel.write(writeBuffer);
            System.out.println("write...");
            readBuffer.clear();
            socketChannel.read(readBuffer);
            System.out.println("received ： " + new String(readBuffer.array()));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        socketChannel.close();
    }
}

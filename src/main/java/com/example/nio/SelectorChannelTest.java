package com.example.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author ywyw2424@foxmail.com
 * @date 2018/10/17 16:19
 * @desc
 */
public class SelectorChannelTest {

    public static void main(String[] args) throws Exception{
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.bind(new InetSocketAddress("127.0.0.1",8000));
        channel.configureBlocking(false);//切换成非阻塞模式

        Selector selector = Selector.open();
        SelectionKey key = channel.register(selector, SelectionKey.OP_ACCEPT);

        ByteBuffer readBuffer = ByteBuffer.allocate(1024);

        while(true) {
            int readyChannels = selector.select();
            if(readyChannels == 0) {
                continue;
            }
            Set selectedKeys = selector.selectedKeys();
            Iterator keyIterator = selectedKeys.iterator();
            while(keyIterator.hasNext()) {
                SelectionKey key1 = (SelectionKey) keyIterator.next();
//                keyIterator.remove();
                if(key1.isAcceptable()) {
                    // a connection was accepted by a ServerSocketChannel.
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ);
                } else if (key1.isConnectable()) {
                    // a connection was established with a remote server.
                } else if (key1.isReadable()) {
                    // a channel is ready for reading
                    SocketChannel channel1 = (SocketChannel) key1.channel();
                    readBuffer.clear();
                    channel1.read(readBuffer);
                    System.out.println("received ： " + new String(readBuffer.array()));

//                    readBuffer.flip();
//                    key1.interestOps(SelectionKey.OP_WRITE);
                } else if (key1.isWritable()) {
                    // a channel is ready for writing
                }
                keyIterator.remove();
            }
        }
    }
}

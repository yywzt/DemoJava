package com.example.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author yanzt
 * @date 2018/10/15 15:47
 * @describe
 */
public class FileChannelTest {

    public static void main(String[] args) throws IOException {
        RandomAccessFile file = new RandomAccessFile("E:\\Development\\study\\ideaWorkSpace\\DemoJava\\src\\main\\java\\com\\example\\io\\test.txt", "rw");
        FileChannel channel = file.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(218);
        int read;
        while ((read = channel.read(byteBuffer))!= -1){
            System.out.println("read" + read);
            byteBuffer.flip();//写模式切换成读模式

            while (byteBuffer.hasRemaining()){
                System.out.print((char) byteBuffer.get());
            }
            System.out.println("");
            byteBuffer.clear();//clear方法会清空整个缓冲区  而compact()方法只会清除已经读过的数据
//            byteBuffer.compact();
        }
        file.close();
    }
}

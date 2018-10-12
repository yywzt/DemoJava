package com.example;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
        String str1 = "手机13766665959的网友说：这首歌很怀旧";
        String str2 = "座机0791-8120410打电话说：我想点歌，可忘记歌名了。";
        String str3 = "QQ号33887974发来信息：小萱萱，你主持的节目真好！";
        System.out.println(str1.replaceAll("([^0-9]*)(\\d{7})(\\d{4})(.*)", "$1尾号$3$4"));
        System.out.println(str2.replaceAll("([^0-9]*)(\\d*)-(\\d{3,4})(\\d{4})(.*)", "$1尾号$4$5"));
        System.out.println(str3.replaceAll("([^0-9]*)(\\d*)(\\d{4})(.*)", "$1尾号$3$4"));
    }

    @Test
    public void test(){
        try(OutputStream output = new ByteArrayOutputStream();
                CharArrayWriter writer = new CharArrayWriter()
                ) {
            byte[] bytes1 = "暗金色的光辉".getBytes();
            try {
                writer.write("暗金色的光辉");
                output.write(bytes1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] bytes = ((ByteArrayOutputStream) output).toByteArray();
            char[] chars = writer.toCharArray();
            System.out.println(bytes1.length);
            System.out.println(bytes.length);
            System.out.println(chars);

            CharArrayReader reader = new CharArrayReader(chars);
            int read = 0;
            try {
                while ((read = reader.read()) != -1) {
                    System.out.print((char) read);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSystemPring(){
        System.out.println("as");
        System.err.println("error");
    }

    @Test
    public void test2(){
        try {
            try(BufferedReader bufferedReader = new BufferedReader(new FileReader("D:\\yw\\workspace\\ideaworkspace\\DemoJava\\src\\main\\java\\com\\example\\io\\test.txt"))) {
                String s;
                while ((s = bufferedReader.readLine()) != null) {
                    System.out.println(s);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

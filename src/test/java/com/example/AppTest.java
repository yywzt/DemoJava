package com.example;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
}

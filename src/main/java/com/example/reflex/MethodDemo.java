package com.example.reflex;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yanzt
 * @date 2018/9/11 14:05
 * @describe 反射操作时绕过编译的，都是在运行时刻来执行的
 */
public class MethodDemo {

    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();
        strings.add("abc");

        System.out.println("strings : " + strings);

        Class<? extends List> aClass = strings.getClass();
        try {
            List list = aClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            Method add = aClass.getMethod("add", Object.class);
            add.invoke(strings,123);
            System.out.println(strings.size());
            System.out.println(strings);

            Method grow1 = aClass.getDeclaredMethod("grow", int.class);
            System.out.println(grow1);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }
}

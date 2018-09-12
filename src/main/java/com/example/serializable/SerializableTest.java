package com.example.serializable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author yanzt
 * @date 2018/9/11 11:28
 * @describe
 */
public class SerializableTest {

    public static void serialTest() throws Exception{
        Person person = new Person(1,"abc");
        System.out.println("person:" + person);
        FileOutputStream fos = new FileOutputStream("person.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(person);
        oos.flush();
        oos.close();
        fos.close();
    }

    public static void deserialTest() throws Exception {
        FileInputStream fis = new FileInputStream("person.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Person person = (Person) ois.readObject();
        ois.close();
        fis.close();
        System.out.println("deserial person : " + person);

    }

    public static void main(String[] args) {
        try {
//            serialTest();
            deserialTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

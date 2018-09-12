package com.example.serializable;

import java.io.Serializable;

/**
 * @author yanzt
 * @date 2018/9/11 11:23
 * @describe
 */
public class Person implements Serializable {

    private static final long serialVersionUID = 1234567890L;

    private long id;
    public String name;
    public String city;

    public Person(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Person(long id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}

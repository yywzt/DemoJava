package com.example.thread;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Java线程取款存款
 * */
public class BankAccount {
    public static void main(String[] args){
        Account account = new Account();
        Random random = new Random();
        Deposit deposit1 = new Deposit(account, random.nextInt(100));
        Deposit deposit2 = new Deposit(account, random.nextInt(100));
        Deposit deposit3 = new Deposit(account, random.nextInt(100));
        Deposit deposit4 = new Deposit(account, random.nextInt(100));
        Withdraw withdraw1 = new Withdraw(account,random.nextInt(100));
        Withdraw withdraw2 = new Withdraw(account,random.nextInt(100));
        Withdraw withdraw3 = new Withdraw(account,random.nextInt(100));
        Withdraw withdraw4 = new Withdraw(account,random.nextInt(100));
        deposit1.start();
        deposit2.start();
        deposit3.start();
        deposit4.start();
        withdraw1.start();
        withdraw2.start();
        withdraw3.start();
        withdraw4.start();
    }
}

/**
 * 余额类
 * */
class Account{

    private double money;

    private ReentrantLock lock;

    public Account(){
        this.money = 0.0d;
        this.lock = new ReentrantLock();
    }

    public Account(double money) {
        this.money = money;
        this.lock = new ReentrantLock();
    }

    public double getMoney() {
        return money;
    }

    public ReentrantLock getLock() {
        return lock;
    }

    public void addMoney(double m){
        money += m;
    }

    public void descMoney(double m){
        money -=m;
    }
}

/**
 * 存钱类
 * */
class Deposit extends Thread{

    private Account account;
    private double money;
    private ReentrantLock lock;

    public Deposit(Account account,double money){
        this.account = account;
        this.money = money;
        this.lock = account.getLock();
    }

    @Override
    public void run() {
        lock.lock();
        try {
            System.out.print("当前余额：" + account.getMoney() + " 存入：" + money);
            account.addMoney(money);
            System.out.println("，成功，余额为" + account.getMoney());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}

/**
 * 取钱类
 * */
class Withdraw extends Thread{

    private Account account;
    private double money;
    private ReentrantLock lock;

    public Withdraw(Account account,double money){
        this.account = account;
        this.money = money;
        this.lock = account.getLock();
    }

    @Override
    public void run() {
        lock.lock();
        try {
            System.out.print("当前余额：" + account.getMoney() + " 取出：" + money);
            if (account.getMoney() >= money) {
                account.descMoney(money);
                System.out.println("，成功，余额为" + account.getMoney());
            } else {
                System.out.println("，失败，余额不足");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
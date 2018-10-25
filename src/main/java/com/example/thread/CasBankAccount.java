package com.example.thread;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Java线程取款存款
 */
public class CasBankAccount {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        CasAccount casAccount = new CasAccount();
        for (int i = 0; i < 10; i++) {
            CasDeposit casDeposit = new CasDeposit(casAccount, ThreadLocalRandom.current().nextLong(100));
            CasWithdraw casWithdraw = new CasWithdraw(casAccount, ThreadLocalRandom.current().nextLong(100));
            casDeposit.start();
            casWithdraw.start();
            casDeposit.join();
            casWithdraw.join();
        }

        long end = System.currentTimeMillis();
        System.out.println("start: " + start + "," + "end: " + end);
        System.out.println("time: " + (end - start));
    }
}

/**
 * 余额类
 */
class CasAccount {

    private AtomicLong money;

    public CasAccount() {
        this.money = new AtomicLong(0L);
    }

    public CasAccount(Long money) {
        this.money = new AtomicLong(money);
    }

    public Long getMoney() {
        return money.get();
    }

    public void addMoney(Long m) {
        Long oldM = 0L;
        Long newM = 0L;
        do {
            oldM = getMoney();
            newM = oldM + m;
        }while(!money.compareAndSet(oldM,newM));
    }

    public void descMoney(Long m) {
        Long oldM = 0L;
        oldM = getMoney();
        Long newM = 0L;
        if(oldM < m ){
            System.out.println("，失败，余额不足");
        }else {
            synchronized (this) {
                if(oldM < m ){
                    System.out.println("，失败，余额不足");
                }else {
                    do {
                        oldM = getMoney();
                        newM = oldM - m;
                    } while (!money.compareAndSet(oldM, newM));
                }
            }
        }
    }
}

/**
 * 存钱类
 */
class CasDeposit extends Thread {

    private CasAccount casAccount;
    private Long money;

    public CasDeposit(CasAccount casAccount, Long money) {
        this.casAccount = casAccount;
        this.money = money;
    }

    @Override
    public void run() {
        System.out.print("当前余额：" + casAccount.getMoney() + " 存入：" + money);
        try {
            casAccount.addMoney(money);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("存款后---，成功，余额为" + casAccount.getMoney());
    }
}

/**
 * 取钱类
 */
class CasWithdraw extends Thread {

    private CasAccount casAccount;
    private Long money;

    public CasWithdraw(CasAccount casAccount, Long money) {
        this.casAccount = casAccount;
        this.money = money;
    }

    @Override
    public void run() {
        System.out.print("当前余额：" + casAccount.getMoney() + " 取出：" + money);
        try {
            if (casAccount.getMoney() >= money) {
                casAccount.descMoney(money);
                System.out.println("取款后---，成功，余额为" + casAccount.getMoney());
            } else {
                System.out.println("，失败，余额不足");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
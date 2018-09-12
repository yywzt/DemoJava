package com.example.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yanzt
 * @date 2018/9/11 17:17
 * @describe 求四位数的吸血鬼数字  两位数*两位数=四位数 、ab*cd=(有abcd组成的四位数)
 */
public class printVampireNumber {

    public static void getVampireNumber(){
        int result;
        String[] resultNum = null;
        String[] mulNum = null;
        List<Integer> results = new ArrayList<>();
        for(int i=10;i<100;i++){
            for(int j=10;j<100;j++){
                result = i*j;
                if(result<1000 || result>=10000){
                    continue;
                }
                resultNum = String.valueOf(result).split("");
                mulNum = (String.valueOf(i) + String.valueOf(j)).split("");
                Arrays.sort(resultNum);
                Arrays.sort(mulNum);
                boolean equals = Arrays.equals(resultNum, mulNum) && !String.valueOf(result).substring(2,4).equals("00");
                if (equals){
                    results.add(result);
//                    System.out.println("i="+i+",j="+j+",result : " + result);
                }
            }
        }
        //去除重复
        List<Integer> collect = results.stream().distinct().collect(Collectors.toList());
        System.out.println(results);
        System.out.println(collect);
    }

    public static void main(String[] args){
        getVampireNumber();
    }
}

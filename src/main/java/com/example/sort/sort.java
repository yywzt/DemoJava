package com.example;

/**
 * 排序算法
 * */
public class sort {

    static int[] a = {32,43,23,13,5};

    public static void main(String[] args){
//       selectSort(a);
//        Arrays.sort(a);
        print();
        quickSort(a,0,a.length-1);
        print();
    }

    public static void print(){
        for(int i=0;i<a.length;i++){
            System.out.print(a[i]+",");
        }
        System.out.println("");
    }

    /**
     * 常用于取序列中最大最小的几个数时。
     * */
    public static void selectSort(int[] a) {
        int length = a.length;
        for (int i = 0; i < length; i++) {//循环次数
            int key = a[i];
            int position=i;
            for (int j = i + 1; j < length; j++) {//选出最小的值和位置
                if (a[j] < key) {
                    key = a[j];
                    position = j;
                }
            }
            a[position]=a[i];//交换位置
            a[i]=key;
            print();
        }
    }

    /**
     * 要求时间最快时。
     * */
    public static void quickSort(int[] numbers, int start, int end) {
        if (start < end) {
            int base = numbers[start]; // 选定的基准值（第一个数值作为基准值）
            int temp; // 记录临时中间值
            int i = start, j = end;
            do {
                while ((numbers[i] < base) && (i < end)) {
                    i++;
                }
                while ((numbers[j] > base) && (j > start)) {
                    j--;
                }
                if (i <= j) {
                    temp = numbers[i];
                    numbers[i] = numbers[j];
                    numbers[j] = temp;
                    i++;
                    j--;
                }
            } while (i <= j);
            if (start < j) {
                quickSort(numbers, start, j);
            }
            if (end > i) {
                quickSort(numbers, i, end);
            }
        }
    }
}

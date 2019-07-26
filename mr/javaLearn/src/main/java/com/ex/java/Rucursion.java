package com.ex.java;

/**
 * @Author: 孟红全
 * @Date: 2019/7/25 上午10:34
 * @Version 1.0
 */
public class Rucursion {
    public int fbn(int n) {
        if(n == 1 || n==2) {
            return 1;
        } else {
            return fbn(n-1)+fbn(n-2);
        }
    }

    public static void main(String[] args) {
        Rucursion rucursion = new Rucursion();
        System.out.println(rucursion.fbn(4));
    }
}



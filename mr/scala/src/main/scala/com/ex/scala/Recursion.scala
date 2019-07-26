package com.ex.scala

/**
  * @Author: 孟红全
  * @Date: 2019/7/25 上午10:23
  * @Version 1.0
  */
object Recursion {
  def main(args: Array[String]): Unit = {
    //斐波那契
    print(fbn(3))

  }

  def fbn(n:Int):Int={
    if(n==1 || n==2) {
      1
    }  else fbn(n-1)+fbn(n-2)
  }

}

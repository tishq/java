import java.util.Scanner;

/**
 * @Author: 孟红全
 * @Date: 2019/8/11 上午9:44
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

//        输入一个行字符串
        System.out.println("输入一行字符串");
         String str = scanner.nextLine();
        System.out.println(str);

//        判断是否是整数
//        输入一个整数
        System.out.println("输入一个整数");
        if(scanner.hasNextInt()) {
            int a = scanner.nextInt();
            System.out.println(a);
        }


        System.out.println("hello");
    }
}
